package com.jwt.project.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemDto {
    private int cartItemId;
    private int quantity;
    private double totalprice;

    @JsonIgnore
    private CartDto cart;
    private ProductDto product;

}
