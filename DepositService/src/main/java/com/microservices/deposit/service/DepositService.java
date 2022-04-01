package com.microservices.deposit.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservices.deposit.controller.dto.DepositResponseDto;
import com.microservices.deposit.entity.Deposit;
import com.microservices.deposit.exception.DepositServiceException;
import com.microservices.deposit.repository.DepositRepository;
import com.microservices.deposit.rest.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Service
public class DepositService {

    private static final String TOPIC_EXCHANGE_DEPOSIT = "js.deposit.notify.exchange";
    private static final String ROUTING_KEY_DEPOSIT = "js.key.deposit";

    private final DepositRepository repository;
    private final AccountServiceClient accountServiceClient;
    private final BillServiceClient billServiceClient;
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public DepositService(DepositRepository repository, AccountServiceClient accountServiceClient,
                          BillServiceClient billServiceClient, RabbitTemplate rabbitTemplate) {
        this.repository = repository;
        this.accountServiceClient = accountServiceClient;
        this.billServiceClient = billServiceClient;
        this.rabbitTemplate = rabbitTemplate;
    }

    public DepositResponseDto deposit(Long accountId, Long billId, BigDecimal amount) throws JsonProcessingException {
        if (accountId == null && billId == null) {
            throw new DepositServiceException("Account is null and bill is null");
        }

        if (billId != null) {
            BillResponseDto billResponseDto = billServiceClient.getBillById(billId);

            BillRequestDto billRequestDto = createBillRequestDto(amount, billResponseDto);

            billServiceClient.update(billId, billRequestDto);

            AccountResponseDto accountResponseDto = accountServiceClient.getAccountById(billResponseDto.getAccountId());
            return createResponse(billId, amount, accountResponseDto);
        }

        BillResponseDto defaultBill = getBillDefault(accountId);
        BillRequestDto billRequestDto = createBillRequestDto(amount, defaultBill);

        billServiceClient.update(defaultBill.getBillId(), billRequestDto);
        AccountResponseDto accountResponseDto = accountServiceClient.getAccountById(accountId);

        return createResponse(billId, amount, accountResponseDto);
    }

    private DepositResponseDto createResponse(Long billId, BigDecimal amount, AccountResponseDto accountResponseDto) throws JsonProcessingException {
        repository.save(new Deposit(amount, billId, OffsetDateTime.now(), accountResponseDto.getEmail()));

        DepositResponseDto depositResponseDto = new DepositResponseDto(amount, accountResponseDto.getEmail());

        ObjectMapper objectMapper = new ObjectMapper();
        rabbitTemplate.convertAndSend(TOPIC_EXCHANGE_DEPOSIT, ROUTING_KEY_DEPOSIT,
                objectMapper.writeValueAsString(depositResponseDto));

        return depositResponseDto;
    }

    private BillRequestDto createBillRequestDto(BigDecimal amount, BillResponseDto billResponseDto) {
        BillRequestDto billRequestDto = new BillRequestDto();
        billRequestDto.setAccountId(billResponseDto.getAccountId());
        billRequestDto.setCreationDate(billResponseDto.getCreationDate());
        billRequestDto.setIsDefault(billResponseDto.getIsDeaful());
        billRequestDto.setOverdraftEnable(billResponseDto.getOverdraftEnable());
        billRequestDto.setAmount(billResponseDto.getAmount().add(amount));
        return billRequestDto;
    }

    private BillResponseDto getBillDefault(Long accountId) {
        return billServiceClient.getBillsByAccountId(accountId)
                .stream()
                .filter(BillResponseDto::getIsDeaful)
                .findFirst()
                .orElseThrow(() -> new DepositServiceException("Default bill didn't found for account:" + accountId));
    }
}

