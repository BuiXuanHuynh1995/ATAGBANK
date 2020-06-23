package com.atag.atagbank.formatter;

import com.atag.atagbank.model.Account;
import com.atag.atagbank.service.account.IAccountService;
import com.atag.atagbank.service.account.impl.AccountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Locale;

@Component
public class AccountFormatter implements Formatter<Account> {
    IAccountService iAccountService;
    @Autowired
    public AccountFormatter (IAccountService iAccountService){
        this.iAccountService = iAccountService;
    }
    @Override
    public Account parse(String text, Locale locale) throws ParseException {
        return iAccountService.findById(Long.parseLong(text));
    }

    @Override
    public String print(Account object, Locale locale) {
        return "[" + object.getId() + ", " +object.getBalance() + "]";
    }
}
