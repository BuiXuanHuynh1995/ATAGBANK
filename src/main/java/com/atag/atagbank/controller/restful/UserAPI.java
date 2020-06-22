package com.atag.atagbank.controller.restful;

import com.atag.atagbank.model.MyUser;
import com.atag.atagbank.service.account.AccountService;
import com.atag.atagbank.service.account.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@SessionAttributes("currentUser")
@RequestMapping("/api/user")
public class UserAPI {

    @Autowired
    IAccountService accountService;

    @ModelAttribute("currentUser")
    MyUser recentUser(){
        return new MyUser();
    }

    @PostMapping("/makeDeposit")
    ResponseEntity<Void> makeDeposit(@SessionAttribute("currentUser") MyUser user, @RequestParam String amount){
        accountService.addMoneyToAccount(Float.parseFloat(amount), user.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
