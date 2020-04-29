package com.github.qcyin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class TestController {

    @ResponseBody
    @GetMapping("/test")
    public Object test(){
        return "test";
    }

    @GetMapping("/index")
    public String index(){
        return "index";
    }

    @GetMapping("/login.html")
    public String loginPage(){
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password){
        return "index";
    }


    @GetMapping("/login/failure")
    public String loginFailure(){
        return "login";
    }

    @GetMapping("/login/success")
    public String loginSuccess(){
        return "index";
    }

    @GetMapping("/logout")
    public String logout(){
        return "login";
    }

}
