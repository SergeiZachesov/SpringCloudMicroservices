package com.microservices.deposit.rest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountResponseDto {

    private Long accountId;

    private String name;

    private String phone;

    private String email;

    private List<Long> bills;

    private OffsetDateTime creationDate;

}
