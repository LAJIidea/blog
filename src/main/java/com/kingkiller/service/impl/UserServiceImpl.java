package com.kingkiller.service.impl;

import com.kingkiller.dto.UserDto;
import com.kingkiller.mapper.UserMapper;
import com.kingkiller.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public int findName(String name) {
        int count = userMapper.findName(name);
        return count;
    }

    @Override
    public void add(String name, String password) {
        userMapper.add(name,password);
    }


    @Override
    public UserDto checkLogin(String name) {
        UserDto user = userMapper.checkLogin(name);
        return user;
    }
}
