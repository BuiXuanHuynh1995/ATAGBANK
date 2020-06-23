package com.atag.atagbank.controller;

import com.atag.atagbank.model.MyUser;
import com.atag.atagbank.service.user.MyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
    public ModelAndView getLoginForm(@ModelAttribute MyUser currentUser) {
        ModelAndView modelAndView = new ModelAndView("login");
        modelAndView.addObject("currentUser", currentUser);
        return modelAndView;
    }

    @PostMapping("/login-form")
    public ModelAndView login(@ModelAttribute MyUser currentUser, HttpSession session) {
        MyUser loginUser = myUserService.findByUserName(currentUser.getUsername());
        if (loginUser != null) {
            session.setAttribute("currentUser", loginUser);
            session.setAttribute("currentUserName", loginUser.getName());
            ModelAndView modelAndView = new ModelAndView("index");
            modelAndView.addObject("currentUser", loginUser);
            return modelAndView;
        }
        return new ModelAndView("login","notFound","Wrong username or password!");
    }

    @GetMapping("personal-profile")
    public ModelAndView showProfile() {
        return new ModelAndView("personal/profile");
    }
}