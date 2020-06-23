package com.atag.atagbank.service.account;

import com.atag.atagbank.model.Account;

public interface IAccountService {
    Iterable<Account> findAll();
    Account findById(Long id);
}
