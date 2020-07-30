package com.kingkiller.service;

import com.kingkiller.dto.UserDto;


public interface UserService {

    int findName(String name);

    void add(String name,String password);

    UserDto checkLogin(String name);

}
