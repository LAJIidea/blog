package com.kingkiller.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
public class IndexController {

    @RequestMapping("/")
    public String index(HttpSession session, Model model) {
        String name = (String)session.getAttribute("name");
        model.addAttribute("name",name);
        return "index";
    }

}
