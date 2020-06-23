package com.atag.atagbank.controller;

import com.atag.atagbank.model.MyUser;
import com.atag.atagbank.service.account.IAccountService;
import com.atag.atagbank.service.user.MyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("admin")
public class AdminController {

    @Autowired
    MyUserService myUserService;

    @GetMapping("customerManagement")
    public ModelAndView showManagementForm(@PageableDefault(10) Pageable pageable){
        Page<MyUser> userList = myUserService.findAll(pageable);
        return new ModelAndView("admin/customerManagement","userList",userList);
    }


}
