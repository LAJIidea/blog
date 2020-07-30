package com.kingkiller.controller;

import com.kingkiller.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Date;

@Controller
public class EditorController {

    @Autowired
    private BlogService blogService;

    @RequestMapping("/edit")
    public String edit(HttpSession session, Model model){
        String name = (String)session.getAttribute("name");
        if (name!=null){
            model.addAttribute("name",name);
            return "edit";
        }
        model.addAttribute("msg","请先登陆");
        return "erro";
    }

    @RequestMapping("/publish")
    @ResponseBody
    public String getEdit(@RequestParam(name = "title")String title,
                          @RequestParam(name = "text")String text,HttpSession session){
        String name = (String)session.getAttribute("name");
        Date date = new Date();
        blogService.insert(title,text,name,date);
        int id = blogService.findId(title);
        session.setAttribute("id",id);
        return "发布成功";
    }

}
