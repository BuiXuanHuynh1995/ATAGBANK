package com.atag.atagbank.controller;

import com.atag.atagbank.model.MyUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/user")
public class UserController {

    @GetMapping("/makeDeposit")
    public ModelAndView showMakeDepositForm(@ModelAttribute("currentUser") MyUser currentUser){
        return new ModelAndView("personal/makeDeposit","currentUser",currentUser);
    }
}
