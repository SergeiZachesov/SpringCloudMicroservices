package com.microservices.notification.service;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class DepositResponseDto {

    private BigDecimal amount;

    private String mail;
}
