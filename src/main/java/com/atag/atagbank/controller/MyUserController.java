package com.atag.atagbank.controller;

import com.atag.atagbank.model.MyUser;
import com.atag.atagbank.service.user.MyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MyUserController {
    @Autowired
    MyUserService myUserService;

    @GetMapping("/admin/list-user")
    public ResponseEntity<Page<MyUser>> showUsers(Pageable pageable) {
        Page<MyUser> users = myUserService.findAll(pageable);
        if (users == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping("/admin/create-user")
    public ResponseEntity<MyUser> createUser(@RequestBody MyUser user) {
        myUserService.save(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("admin/update-user/{id}")
    public ResponseEntity<MyUser> updateUser(@PathVariable Long id, @RequestBody MyUser user) {
        MyUser currentUser = myUserService.findById(id);
        if (currentUser == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        user.setId(id);
        myUserService.save(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
