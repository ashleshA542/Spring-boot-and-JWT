package com.jwt.project.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemDto {


    private int orderItemId;

    private ProductDto product;

    private double totalProductprice;
    @JsonIgnore
    private OrderDto order;
}
