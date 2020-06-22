package com.atag.atagbank.controller;

import com.atag.atagbank.model.Transaction;
import com.atag.atagbank.service.transaction.ITransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    ITransactionService iTransactionService;

    @GetMapping("/listing")
    ModelAndView getAllTransaction(Pageable pageable){
        ModelAndView modelAndView = new ModelAndView("/transaction/listing");
        Page<Transaction> transactions = iTransactionService.findAll(pageable);
        modelAndView.addObject("transactions",transactions);
        return modelAndView;
    }
}
