package com.atag.atagbank.service.account;

import com.atag.atagbank.model.Account;
import com.atag.atagbank.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Component
public class AccountService implements IAccountService{
    @Autowired
    AccountRepository accountRepository;

    @Override
    public Iterable<Account> findAll() {
        return accountRepository.findAll();
    }

    @Override
    public Optional<Account> findById(Long id) {
        return accountRepository.findById(id);
    }

    @Override
    public void save(Account account) {
        accountRepository.save(account);
    }

    @Override
    public void addMoneyToAccount(Float amount, Long id) {
        Optional<Account> currentAccountOptional = this.findById(id);
        if (currentAccountOptional.isPresent()){
            Account currentAccount = currentAccountOptional.get();
            currentAccount.setBalance(currentAccount.getBalance()+amount);
            save(currentAccount);
        }
    }
}
