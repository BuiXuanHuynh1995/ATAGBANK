package com.atag.atagbank.controller;

import com.atag.atagbank.model.MyUser;
import com.atag.atagbank.model.Role;
import com.atag.atagbank.service.user.MyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Controller
@SessionAttributes("currentUser")
public class MainController {

    @ModelAttribute("currentUser")
    public MyUser getCurrentUser() {
        return new MyUser();
    }

    @Autowired
    MyUserService myUserService;

    @GetMapping("/")
    public ModelAndView getHomePage() {
        return new ModelAndView("index");
    }

    @GetMapping("/login-form")
    public ModelAndView getLoginForm(@ModelAttribute MyUser currentUser) {
        ModelAndView modelAndView = new ModelAndView("login");
        modelAndView.addObject("currentUser", currentUser);
        return modelAndView;
    }

   @PostMapping("/login-form")
    public ModelAndView login(@ModelAttribute MyUser currentUser, HttpSession session) {
        MyUser loginUser = myUserService.findByUserName(currentUser.getUsername());
        if (loginUser != null && currentUser.getPassword().equals(loginUser.getPassword())) {
            if (loginUser.isEnabled()) {
                session.setAttribute("currentUser", loginUser);
                session.setAttribute("currentUserName", loginUser.getName());
                ModelAndView modelAndView = new ModelAndView("index");
                modelAndView.addObject("currentUser", loginUser);
                return modelAndView;
            } else {
                return new ModelAndView("login", "deactivated", "Account is deactivated. Please contact Admin to active!");
            }
        }
        return new ModelAndView("login", "notFound", "Wrong username or password!");
    }

    @GetMapping("/personal-profile")
    public ModelAndView editProfile() {
        return new ModelAndView("personal/profile");
    }

    @PostMapping("/personal-profile")
    public ModelAndView updateProfile(@ModelAttribute MyUser customer) {
        myUserService.save(customer);
        MyUser updatedCustomer = myUserService.findById(customer.getId());
        updatedCustomer.setEnabled(true);
        ModelAndView modelAndView = new ModelAndView("personal/profile");
        modelAndView.addObject("currentUser", updatedCustomer);
        modelAndView.addObject("message", "The information has been updated!");
        return modelAndView;
    }
}