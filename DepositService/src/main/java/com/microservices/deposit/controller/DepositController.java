package com.microservices.deposit.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.microservices.deposit.controller.dto.DepositRequestDto;
import com.microservices.deposit.controller.dto.DepositResponseDto;
import com.microservices.deposit.service.DepositService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DepositController {

    private final DepositService service;

    @Autowired
    public DepositController(DepositService service) {
        this.service = service;
    }

    @PostMapping("/deposits")
    public DepositResponseDto deposit(@RequestBody DepositRequestDto requestDto) throws JsonProcessingException {
        return service.deposit(requestDto.getAccountId(), requestDto.getBillId(), requestDto.getAmount());
    }

}
