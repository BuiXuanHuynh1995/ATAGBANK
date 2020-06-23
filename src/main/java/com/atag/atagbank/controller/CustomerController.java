package com.atag.atagbank.controller;

import com.atag.atagbank.model.MyUser;
import com.atag.atagbank.service.user.MyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CustomerController {
    @Autowired
    MyUserService myUserService;
//
//    @PostMapping("")
//    public ModelAndView editCustomer(@ModelAttribute MyUser customer) {
//        myUserService.save(customer);
//        ModelAndView modelAndView = new ModelAndView("personal/profile");
//
//
//    }
}
