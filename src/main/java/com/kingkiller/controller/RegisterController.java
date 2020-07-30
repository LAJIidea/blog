package com.kingkiller.controller;

import com.kingkiller.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
public class RegisterController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String register(){
        return "register";
    }

    @RequestMapping("/check")
    @ResponseBody
    public String check(String name){
        int count = userService.findName(name);
        if (count>0){
            return "用户已存在";
        }
        return "OK";
    }

    @PostMapping("/register")
    public String register(@RequestParam(name = "username")String name,
                           @RequestParam(name = "password")String password, HttpSession session){
        userService.add(name,password);
        session.setAttribute("name",name);
        return "redirect:/";
    }

}
