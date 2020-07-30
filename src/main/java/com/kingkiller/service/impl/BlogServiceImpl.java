package com.kingkiller.service.impl;

import com.kingkiller.dto.BlogDTO;
import com.kingkiller.mapper.BlogMapper;
import com.kingkiller.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogMapper blogMapper;

    @Override
    public void insert(String title, String text, String name, Date date) {
        blogMapper.insert(title,text,name,date);
    }

    @Override
    public BlogDTO show(Integer id) {
        BlogDTO show = blogMapper.show(id);
        return show;
    }

    @Override
    public void addPhoto(String url, Integer id) {
        blogMapper.addPhoto(url,id);
    }

    @Override
    public String showPhoto(Integer id) {
        String photo = blogMapper.showPhoto(id);
        return photo;
    }

    @Override
    public int findId(String title) {
        int id = blogMapper.findId(title);
        return id;
    }
}
