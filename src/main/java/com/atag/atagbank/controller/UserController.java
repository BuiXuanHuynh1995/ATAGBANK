package com.atag.atagbank.controller;

import com.atag.atagbank.model.MyUser;
import com.atag.atagbank.model.Role;
import com.atag.atagbank.service.account.IAccountService;
import com.atag.atagbank.service.user.MyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    IAccountService accountService;

    @Autowired
    MyUserService myUserService;

    @GetMapping("/makeDeposit")
    public ModelAndView showMakeDepositForm(@SessionAttribute("currentUser") MyUser currentUser) {
        return new ModelAndView("personal/makeDeposit", "currentUser", currentUser);
    }

    @GetMapping("/profile")
    public ModelAndView getPersonalProfile(HttpSession session) {
        String name = (String) session.getAttribute("currentUserName");
        MyUser currentUser = myUserService.findByName(name);
        return new ModelAndView("personal/profile", "currentUser", currentUser);
    }

    @PostMapping("/profile")
    public ModelAndView updateProfile(@ModelAttribute MyUser customer) {
        MyUser user = myUserService.findById(customer.getId());
        Role role = user.getRole();
        customer.setRole(role);
        myUserService.save(customer);
        ModelAndView modelAndView = new ModelAndView("personal/profile");
        modelAndView.addObject("currentUser", customer);
        modelAndView.addObject("message", "The information has been updated!");
        return modelAndView;
    }
}
