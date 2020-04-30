package com.syscom.ssm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Objects;

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


    public static void main(String[] args) {
        String username = "admin";
        String password = "000000";
        String salt1 = "Rita123321";
        String salt2 = "1234567890Rossweisse";
        int usernamePart = username.length() >> 2;
        int passwordPart = password.length() >> 2;

        String str = username.substring(usernamePart) + password.substring(0, passwordPart)
                + username.substring(0, usernamePart) + password.substring(passwordPart);
        int hashCode = str.hashCode();

        String newPassword = salt1 + str + hashCode + salt2.substring(hashCode % 10);
        System.out.println("str = " + str);
        System.out.println("newPassword = " + newPassword);
        System.out.println("scramble = " + scramble(newPassword));
    }


    public static String scramble(String s) {
        Objects.requireNonNull(s);
        char[] chars = s.toCharArray();
        return new String(scramble(chars, 0, chars.length));
    }

    public static char[] scramble(char[] chars, int start, int end) {
        int mid = (start + end) >> 1;
        if (mid == start || mid == end) {
            return new char[]{chars[mid]};
        }else {
            char[] left = scramble(chars, start, mid);
            char[] right = scramble(chars, mid, end);
            char[] newChars = new char[left.length + right.length];
            if (left.length == 1 || right.length == 1) {
                System.arraycopy(right, 0, newChars, 0, right.length);
                System.arraycopy(left, 0, newChars, right.length, left.length);
            } else {
                System.arraycopy(left, 0, newChars, 0, left.length);
                System.arraycopy(right, 0, newChars, left.length, right.length);
            }
            return newChars;
        }

    }

}