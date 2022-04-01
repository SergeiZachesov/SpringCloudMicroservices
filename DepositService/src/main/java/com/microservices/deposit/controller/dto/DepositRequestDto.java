package com.microservices.deposit.controller.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class DepositRequestDto {

    private Long accountId;

    private Long billId;

    private BigDecimal amount;
}
