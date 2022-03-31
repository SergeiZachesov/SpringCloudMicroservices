package com.microservices.account.controller;

import com.microservices.account.controller.dto.AccountRequestDto;
import com.microservices.account.controller.dto.AccountResponseDto;
import com.microservices.account.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class AccountController {

    private final AccountService service;

    @Autowired
    public AccountController(AccountService service) {
        this.service = service;
    }

    @GetMapping("/{accountId}")
    public AccountResponseDto getAccount(@PathVariable Long accountId) {
        return new AccountResponseDto(service.getAccountById(accountId));
    }

    @PostMapping("/")
    public Long createAccount(@RequestBody AccountRequestDto accountRequestDto) {
        return service.createAccount(accountRequestDto.getName(),
                accountRequestDto.getEmail(),
                accountRequestDto.getPhone(),
                accountRequestDto.getBills());
    }

    @PutMapping("/{accountId}")
    public AccountResponseDto updateAccount(@PathVariable Long accountId,
                                            @RequestBody AccountRequestDto accountRequestDto) {
        return new AccountResponseDto(service.updateAccount(accountId, accountRequestDto.getName(),
                accountRequestDto.getEmail(),
                accountRequestDto.getPhone(),
                accountRequestDto.getBills()));
    }

    @DeleteMapping("/{accountId}")
    public AccountResponseDto deleteAccount(@PathVariable Long accountId) {
        return new AccountResponseDto(service.deleteAccount(accountId));
    }
}
