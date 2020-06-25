package com.atag.atagbank.controller;

import com.atag.atagbank.model.Account;
import com.atag.atagbank.model.MyUser;
import com.atag.atagbank.model.Role;
import com.atag.atagbank.service.account.IAccountService;
import com.atag.atagbank.service.role.IRoleService;
import com.atag.atagbank.service.user.MyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@RestController
//@RequestMapping("/admin")
public class AdminController {

    @Autowired
    MyUserService myUserService;

    @Autowired
    IRoleService roleService;

    @Autowired
    IAccountService accountService;

    @ModelAttribute("roleList")
    Iterable<Role> roleList(){
        return roleService.findAll();
    }


    @GetMapping("/admin/customerManagement")
    public ModelAndView showManagementForm(@PageableDefault(10) Pageable pageable){
        Page<MyUser> userList = myUserService.findAll(pageable);
        return new ModelAndView("admin/customerManagement","userList",userList);
    }

    @GetMapping("/admin/user-update/{id}")
    public ModelAndView showUpdateForm(@PathVariable Long id , @ModelAttribute("user") MyUser user){
        ModelAndView modelAndView = new ModelAndView("admin/editCustomer");
        user = myUserService.findById(id);
        modelAndView.addObject("user",user);
        return modelAndView;
    }

    @PostMapping("/admin/user-update")
    public ModelAndView updateUser(@ModelAttribute MyUser user) {
        ModelAndView modelAndView = new ModelAndView("admin/editCustomer");
        myUserService.save(user);
        modelAndView.addObject("user",user);
        modelAndView.addObject("announcement","Successfully!");
        return modelAndView;
    }
//
//    @GetMapping("/admin/createNewCustomer")
//    public ModelAndView showCreateForm() {
//        ModelAndView modelAndView = new ModelAndView("admin/createCustomer");
//        modelAndView.addObject("user",new MyUser("","","","",true,"","","",new Role(),new Account(),""));
//        return modelAndView;
//    }

    @GetMapping("/admin/createNewCustomer")
    public ModelAndView showCreateForm() {
        ModelAndView modelAndView = new ModelAndView("admin/createCustomer");
        modelAndView.addObject("user",new MyUser());
        return modelAndView;
    }

    @PostMapping("/admin/createNewCustomer")
    public ModelAndView createNewCustomer(@Validated @ModelAttribute("user") MyUser user, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            ModelAndView modelAndView = new ModelAndView("admin/createCustomer");
            return modelAndView;
        }

        if (myUserService.findByUserName(user.getUsername())!=null){
            ModelAndView modelAndView = new ModelAndView("admin/createCustomer");
            modelAndView.addObject("user",user);
            modelAndView.addObject("announce","Already exist!");
            return modelAndView;
        }

        List<MyUser> userList = myUserService.findAllList();
        user.setId(Long.parseLong(String.valueOf(userList.size()+1)));
        Account account = new Account();

        Date now = new Date();
        String accountNumber = new SimpleDateFormat("yyyyMMddHHmmss", Locale.ENGLISH).format(now);

        account.setId(Long.parseLong(accountNumber));
        account.setBalance(0F);

        user.setAccount(account);
        accountService.save(account);
        myUserService.save(user);

        ModelAndView modelAndView = new ModelAndView("admin/createCustomer");
        modelAndView.addObject("user",user);
        modelAndView.addObject("announceSuccess","Successfully!");

        return modelAndView;
    }

    @GetMapping("/admin/searchUser")
    public ModelAndView searchUser(@Param("keyword")String keyword){
        ModelAndView modelAndView =new ModelAndView("list");
        List<MyUser> users =myUserService.findByNameOrAccountOrAndAddressLike("%"+keyword+"%");
        modelAndView.addObject("userList",users);
        return modelAndView;
    }
}
