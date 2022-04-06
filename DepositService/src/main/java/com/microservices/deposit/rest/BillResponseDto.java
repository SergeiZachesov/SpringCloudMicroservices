package com.microservices.deposit.rest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class BillResponseDto {
    private Long billId;

    private Long accountId;

    private BigDecimal amount;

    private Boolean isDeaful;

    private OffsetDateTime creationDate;

    private Boolean overdraftEnable;
}
