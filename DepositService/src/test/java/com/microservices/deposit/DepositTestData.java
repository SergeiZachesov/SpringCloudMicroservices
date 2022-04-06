package com.microservices.deposit;

import com.microservices.deposit.rest.AccountResponseDto;
import com.microservices.deposit.rest.BillResponseDto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Arrays;

public class DepositTestData {

    public static BillResponseDto createBillResponseDTO() {
        BillResponseDto billResponseDto = new BillResponseDto();
        billResponseDto.setBillId(1L);
        billResponseDto.setAccountId(1L);
        billResponseDto.setAmount(new BigDecimal(1000));
        billResponseDto.setCreationDate(OffsetDateTime.now());
        billResponseDto.setIsDeaful(true);
        billResponseDto.setOverdraftEnable(true);
        return billResponseDto;
    }

    public static AccountResponseDto createAccountResponseDto() {
        AccountResponseDto accountResponseDto = new AccountResponseDto();
        accountResponseDto.setAccountId(1L);
        accountResponseDto.setBills(Arrays.asList(1L, 2L, 3L));
        accountResponseDto.setCreationDate(OffsetDateTime.now());
        accountResponseDto.setName("Name");
        accountResponseDto.setEmail("m@eamail.ru");
        accountResponseDto.setPhone("+72856");
        return accountResponseDto;
    }
}
