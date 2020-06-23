package com.atag.atagbank.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("admin")
public class AdminController {

    @GetMapping("customerManagement")
    public ModelAndView showManagementForm(){
        return new ModelAndView("admin/customerManagement");
    }

}
