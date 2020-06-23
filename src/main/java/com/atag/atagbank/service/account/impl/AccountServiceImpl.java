package com.atag.atagbank.service.account.impl;

import com.atag.atagbank.model.Account;
import com.atag.atagbank.repository.AccountRepository;
import com.atag.atagbank.service.account.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements IAccountService {
    @Autowired
    AccountRepository accountRepository;
    @Override
    public Iterable<Account> findAll() {
        return accountRepository.findAll();
    }

    @Override
    public Account findById(Long id) {
        return accountRepository.getOne(id);
    }
}
