package com.kingkiller.service;

import com.kingkiller.dto.BlogDTO;

import java.util.Date;

public interface BlogService {

    void insert(String title, String text, String name, Date date);

    BlogDTO show(Integer id);

    void addPhoto(String url,Integer id);

    String showPhoto(Integer id);

    int findId(String title);
}
