package com.atag.atagbank.controller;

import com.atag.atagbank.model.Account;
import com.atag.atagbank.model.Transaction;
import com.atag.atagbank.service.account.IAccountService;
import com.atag.atagbank.service.transaction.ITransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Timestamp;
import java.util.Date;

@Controller
@RequestMapping("/transaction")
public class TransactionController {
    @Autowired
    ITransactionService iTransactionService;
    @Autowired
    IAccountService iAccountService;

    @Autowired
    @ModelAttribute("accounts")
    Iterable<Account> accounts() {
        return iAccountService.findAll();
    }

    @GetMapping("/listing")
    ModelAndView getAllTransaction(Pageable pageable) {
        ModelAndView modelAndView = new ModelAndView("transaction/listing");
        Page<Transaction> transactions = iTransactionService.findAll(pageable);
        modelAndView.addObject("transactions", transactions);
        return modelAndView;
    }

    @GetMapping("/create")
    ModelAndView showCreateTransactionForm() {
        ModelAndView modelAndView = new ModelAndView("transaction/create");
        modelAndView.addObject("transaction", new Transaction());
        return modelAndView;
    }

    @PostMapping("/create")
    ModelAndView createTransaction(@ModelAttribute("transaction") Transaction transaction) {
        transaction.setType(false);
        transaction.setTime(new Timestamp(System.currentTimeMillis()));
        iTransactionService.save(transaction);
        ModelAndView modelAndView = new ModelAndView("transaction/create");
        modelAndView.addObject("transaction", new Transaction());
        modelAndView.addObject("message", "Send successfully");
        return modelAndView;
    }
}
