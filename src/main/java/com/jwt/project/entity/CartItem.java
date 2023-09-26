package com.jwt.project.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cartItemId;
    private int quantity;
    private double totalprice;


    @ManyToOne
    @JsonBackReference
    private Cart cart;

    @OneToOne
    private Product product;




















































   /* @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CartItem cartItem = (CartItem) o;

        return cartItemId == cartItem.cartItemId;
    }

    @Override
    public int hashCode() {
        return cartItemId;
    }*/

}
