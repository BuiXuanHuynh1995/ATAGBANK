package com.atag.atagbank.service.account;

import com.atag.atagbank.model.Account;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface IAccountService {
    Iterable<Account> findAll();
    Optional<Account> findById(Long id);
    void addMoneyToAccount(Float amount, Long id);
    void save(Account account);
    void minusMoneyFromAccount(Float amount,Long id);
}
