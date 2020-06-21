package com.atag.atagbank.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping("/")
    public String getMainIndex(){
        return "index";
    }

    @GetMapping("/personal")
    public String getPersonalIndex(){
        return "personal/index";
    }
}
