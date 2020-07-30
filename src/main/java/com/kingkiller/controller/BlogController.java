package com.kingkiller.controller;

import com.kingkiller.dto.BlogDTO;
import com.kingkiller.service.BlogService;
import com.kingkiller.util.MarkDownUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
public class BlogController {

    @Autowired
    private BlogService blogService;

    @RequestMapping("/blog/{id}")
    public String blog(@PathVariable(name = "id")Integer id, HttpSession session, Model model){
        String name = (String)session.getAttribute("name");
        model.addAttribute("name",name);
        BlogDTO show = blogService.show(id);
        String text = MarkDownUtil.mdToHtml(show.getText());
        model.addAttribute("text",text);
        model.addAttribute("blog",show);
        return "blog";
    }

}
