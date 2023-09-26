package com.jwt.project.dto;

import com.jwt.project.entity.Cart;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {

    private String userName;

    private String userFirstName;

    private String userLastName;
    private String userPassword;

}
