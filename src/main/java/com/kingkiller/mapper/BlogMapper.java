package com.kingkiller.mapper;

import com.kingkiller.dto.BlogDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;


import java.util.Date;

@Mapper
public interface BlogMapper {

    @Insert("insert into blog(title,text,publisher,date) values(#{title},#{text},#{name},#{date})")
    void insert(String title, String text, String name, Date date);

    @Select("select * from blog where id=#{id}")
    BlogDTO show(Integer id);

    @Insert("update blog set url=#{url} where id = #{id}")
    void addPhoto(String url,Integer id);

    @Select("select url from blog where id=#{id}")
    String showPhoto(Integer id);

    @Select("select id from blog where title = #{title}")
    int findId(String title);

}
