package com.kingkiller.controller;

import com.kingkiller.dto.UserDto;
import com.kingkiller.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;


@Controller
@Slf4j
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login(HttpSession session, Model model){
        String msg = (String)session.getAttribute("msg");
        log.info("接收到的msg {}", msg);
        model.addAttribute("msg",msg);
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam(name = "username")String username,
                        @RequestParam(name = "password")String password, HttpSession session){
        UserDto check = userService.checkLogin(username);
        if (check!=null){
            if (check.getPassword().equals(password)){
                session.setAttribute("name",username);
                return "redirect:/";
            }else {
                session.setAttribute("msg","密码错误");
                return "redirect:/login";
            }
        }else {
                session.setAttribute("msg","用户名不存在");
            return "redirect:/login";
        }
    }

    @RequestMapping("/loginout")
    public String loginOut(HttpSession session){
        session.invalidate();
        return "redirect:/";
    }

}
