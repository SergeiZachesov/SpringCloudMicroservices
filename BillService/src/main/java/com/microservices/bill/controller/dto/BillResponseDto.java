package com.microservices.bill.controller.dto;

import com.microservices.bill.entity.Bill;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@AllArgsConstructor
@Getter

public class BillResponseDto {
    private Long billId;

    private Long accountId;

    private BigDecimal amount;

    private Boolean isDeaful;

    private OffsetDateTime creationDate;

    private Boolean overdraftEnable;

    public BillResponseDto(Bill bill) {
        billId = bill.getBillId();
        accountId = bill.getAccountId();
        amount = bill.getAmount();
        isDeaful = bill.getIsDeaful();
        creationDate = bill.getCreationDate();
        overdraftEnable = bill.getOverdraftEnable();
    }
}
