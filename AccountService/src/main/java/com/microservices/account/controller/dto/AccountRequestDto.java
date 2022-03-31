package com.microservices.account.controller.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class AccountRequestDto {

    private String name;

    private String phone;

    private String email;

    private List<Long> bills;
}
