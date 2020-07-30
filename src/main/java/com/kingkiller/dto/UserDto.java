package com.kingkiller.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class UserDto {
    private Integer id;
    private String username;
    private String password;
    private String url;
}
