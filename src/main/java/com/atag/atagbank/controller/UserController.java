package com.atag.atagbank.controller;

import com.atag.atagbank.model.MyUser;
import com.atag.atagbank.service.account.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    IAccountService accountService;

    @GetMapping("/makeDeposit")
    public ModelAndView showMakeDepositForm(@ModelAttribute("currentUser") MyUser currentUser){
        return new ModelAndView("personal/makeDeposit","currentUser",currentUser);
    }
}
