package com.jwt.project.dto;


import com.jwt.project.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
public class CartDto {

    private int cartId;

    private Set<CartItemDto> items= new HashSet<>();

    private User user;

}
