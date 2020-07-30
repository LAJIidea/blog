package com.kingkiller.mapper;

import com.kingkiller.dto.UserDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;


@Mapper
public interface UserMapper {

    @Select("select count(*) from user where username = #{name}")
    int findName(String name);

    @Insert("insert into user(username,password) values(#{name},#{password})")
    void add(String name,String password);

    @Select("select * from user where username=#{name}")
    UserDto checkLogin(String name);

}
