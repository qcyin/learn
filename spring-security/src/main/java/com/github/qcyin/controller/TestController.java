package com.github.qcyin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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

}
