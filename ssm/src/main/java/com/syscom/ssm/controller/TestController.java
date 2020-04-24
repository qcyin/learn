package com.syscom.ssm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author yinqicheng
 */
@Controller
@RequestMapping("/test")
public class TestController {

    @RequestMapping("/index")
    public String index(ModelMap model) {
        model.addAttribute("abc", "abc");
        return "index";
    }

    @ResponseBody
    @GetMapping("/{id}")
    public String restful(@PathVariable("id") String id) {
        return id;
    }
}
