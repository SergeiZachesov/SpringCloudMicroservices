package com.microservices.account.service;

import com.microservices.account.entity.Account;
import com.microservices.account.exeption.AccountNotFoundException;
import com.microservices.account.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public class AccountService {

    private final AccountRepository repository;

    @Autowired
    public AccountService(AccountRepository repository) {
        this.repository = repository;
    }

    public Account getAccountById(Long accountId) {
        return repository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Unable to find account with id:" + accountId));
    }

    public Long createAccount(String name, String email, String phone, List<Long> bills) {
        Account account = new Account(name, phone, email, OffsetDateTime.now(), bills);
        return repository.save(account).getAccountId();
    }

    public Account updateAccount(Long accountId, String name, String email, String phone, List<Long> bills) {
        Account account = new Account();
        account.setAccountId(accountId);
        account.setName(name);
        account.setEmail(email);
        account.setPhone(phone);
        account.setBills(bills);
        return repository.save(account);
    }

    public Account deleteAccount(Long id) {
        Account account = getAccountById(id);
        repository.deleteById(id);
        return account;
    }
}
