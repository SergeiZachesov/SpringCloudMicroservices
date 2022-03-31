package com.microservices.bill.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long billId;

    private Long accountId;

    private BigDecimal amount;

    private Boolean isDeaful;

    private OffsetDateTime creationDate;

    private Boolean overdraftEnable;

    public Bill(Long accountId, BigDecimal amount, Boolean isDeaful, OffsetDateTime creationDate, Boolean overdraftEnable) {
        this.accountId = accountId;
        this.amount = amount;
        this.isDeaful = isDeaful;
        this.creationDate = creationDate;
        this.overdraftEnable = overdraftEnable;
    }

    public Bill(Long accountId, BigDecimal amount, Boolean isDeaful, Boolean overdraftEnable) {
        this.accountId = accountId;
        this.amount = amount;
        this.isDeaful = isDeaful;
        this.overdraftEnable = overdraftEnable;
    }
}
