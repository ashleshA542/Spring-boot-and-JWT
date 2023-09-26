package com.jwt.project.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cart {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cartId;

   //bidirectional mapping
   /*@JsonManagedReference*/
   @OneToMany(mappedBy = "cart",cascade = CascadeType.ALL,orphanRemoval = true)
   private Set<CartItem> items= new HashSet<>();

   @OneToOne
   private User user;


}
