package com.atag.atagbank.controller;

import com.atag.atagbank.model.MyUser;
import com.atag.atagbank.service.account.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    IAccountService accountService;

    @GetMapping("/makeDeposit")
    public ModelAndView showMakeDepositForm(@SessionAttribute("currentUser") MyUser currentUser){
        return new ModelAndView("personal/makeDeposit","currentUser",currentUser);
    }


    @GetMapping("/profile")
    public String getPersonalProfile() {
        return "personal/profile";
    }
}
