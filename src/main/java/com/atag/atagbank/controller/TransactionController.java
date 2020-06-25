package com.atag.atagbank.controller;

import com.atag.atagbank.model.Account;
import com.atag.atagbank.model.MyUser;
import com.atag.atagbank.model.Transaction;
import com.atag.atagbank.service.account.IAccountService;
import com.atag.atagbank.service.exception.TransactionException;
import com.atag.atagbank.service.transaction.ITransactionService;
import com.atag.atagbank.service.user.MyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.transaction.TransactionalException;
import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    ITransactionService iTransactionService;
    @Autowired
    IAccountService iAccountService;
    @Autowired
    MyUserService myUserService;

    @ModelAttribute("accounts")
    public Iterable<Account> accounts() {
        return iAccountService.findAll();
    }

    @GetMapping("/OTPPage")
    public String getOPTPage() {
        return "transaction/OTPPage";
    }

    @GetMapping("/listing")
    ModelAndView getAllTransaction(@PageableDefault(sort = "time", direction = Sort.Direction.DESC) Pageable pageable, HttpSession session) {
        Page<Transaction> transactions;
        ModelAndView modelAndView = new ModelAndView("transaction/listing");
        String currentUserName = (String) session.getAttribute("currentUserName");
        MyUser currentUser = myUserService.findByName(currentUserName);
        transactions = iTransactionService.findAllByAccount(currentUser.getAccount(), pageable);
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
    ModelAndView createTransaction(@Valid @ModelAttribute("transaction") Transaction transaction,BindingResult bindingResult,  HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("transaction/create");
        if (bindingResult.hasFieldErrors()) {
            modelAndView.addObject("errorMessage", "Amount must be positive number!");
            return modelAndView;
        } else {
            String senderName = (String) session.getAttribute("currentUserName");
            MyUser senderAccount = myUserService.findByName(senderName);
            String receiverName = String.valueOf(transaction.getAccount().getId());
            //Receiver scope
            transaction.setType("CREDIT");
            transaction.setPartnerAccount(String.valueOf(senderAccount.getAccount().getId()));
            transaction.setTime(new Timestamp(System.currentTimeMillis()));
            //Sender scope
            Transaction syncTransaction = getSyncTransaction(transaction, senderAccount, receiverName);
            try {
                iAccountService.transfer(senderAccount.getAccount().getId(), transaction.getAccount().getId(), transaction.getAmount());
            } catch (TransactionException transactionException) {
                modelAndView.addObject("errorMessage", transactionException.getMessage());
                return modelAndView;
            }
            iTransactionService.save(syncTransaction);
            iTransactionService.save(transaction);
            modelAndView.addObject("transaction", new Transaction());
            modelAndView.addObject("message", "Send successfully");
            return modelAndView;
        }

    }

    private Transaction getSyncTransaction(@ModelAttribute("transaction") @Valid Transaction transaction, MyUser senderAccount, String receiverName) {
        Transaction syncTransaction = new Transaction();
        syncTransaction.setTime(new Timestamp(System.currentTimeMillis()));
        syncTransaction.setAmount(transaction.getAmount());
        syncTransaction.setType("DEBIT");
        syncTransaction.setAccount(senderAccount.getAccount());
        syncTransaction.setTransactionMessage(transaction.getTransactionMessage());
        syncTransaction.setPartnerAccount(receiverName);
        return syncTransaction;
    }
}
