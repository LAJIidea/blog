package com.kingkiller.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
@Slf4j
public class IndexController {

    @RequestMapping("/")
    public String index(HttpSession session, Model model) {
        String name = (String)session.getAttribute("name");
        model.addAttribute("name",name);
        return "index";
    }

    @RequestMapping("/err")
    public String error(@ModelAttribute("msq")String msg){
        log.info("接收到的msg {}", msg);
        //model.addAttribute("msg",msg);
        return "erro";
    }

}
