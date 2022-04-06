package com.microservices.deposit.service;

import com.microservices.deposit.controller.dto.DepositResponseDto;
import com.microservices.deposit.exception.DepositServiceException;
import com.microservices.deposit.repository.DepositRepository;
import com.microservices.deposit.rest.AccountResponseDto;
import com.microservices.deposit.rest.AccountServiceClient;
import com.microservices.deposit.rest.BillResponseDto;
import com.microservices.deposit.rest.BillServiceClient;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.math.BigDecimal;

import static com.microservices.deposit.DepositTestData.createAccountResponseDto;
import static com.microservices.deposit.DepositTestData.createBillResponseDTO;

@RunWith(MockitoJUnitRunner.class)
public class DepositServiceTest {

    @Mock
    private DepositRepository depositRepository;

    @Mock
    private AccountServiceClient accountServiceClient;

    @Mock
    private BillServiceClient billServiceClient;

    @Mock
    private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private DepositService depositService;

    @Test
    public void depositWithBillId() {
        BillResponseDto billResponseDto = createBillResponseDTO();
        Mockito.when(billServiceClient.getBillById(ArgumentMatchers.anyLong()))
                .thenReturn(billResponseDto);
        AccountResponseDto accountResponseDto = createAccountResponseDto();
        Mockito.when(accountServiceClient.getAccountById(ArgumentMatchers.anyLong()))
                .thenReturn(accountResponseDto);
        DepositResponseDto deposit = depositService.deposit(null, 1L, new BigDecimal(1000));
        Assertions.assertThat(deposit.getMail()).isEqualTo("m@eamail.ru");
    }

    @Test(expected = DepositServiceException.class)
    public void depositWithAccountIdAndBullIdNull() {
        depositService.deposit(null, null, BigDecimal.valueOf(1000));
    }
}