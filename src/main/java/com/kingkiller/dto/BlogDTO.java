package com.kingkiller.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
@Setter
@Getter
public class BlogDTO {
    private Integer id;
    private String title;
    private String publisher;
    private String text;
    private Integer count;
    private Date date;
}
