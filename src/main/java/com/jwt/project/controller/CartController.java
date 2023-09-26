package com.jwt.project.controller;

import com.jwt.project.dto.CartDto;
import com.jwt.project.dto.ItemRequest;
import com.jwt.project.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping({"/api"})
public class CartController {

    @Autowired
    private CartService cartService;



    @PostMapping({"/addtoCart"})
    @PreAuthorize("hasRole('User')")
    public ResponseEntity<CartDto> addtoCart(@RequestBody ItemRequest itemRequest, Principal principal) {
        String username = principal.getName();
        CartDto addItem = cartService.addItem(itemRequest, username);

        return new ResponseEntity<>(addItem, HttpStatus.OK);
    }



    @GetMapping("/")
    @PreAuthorize("hasRole('User')")
    public ResponseEntity<CartDto> getAllCart(Principal principal) {
        CartDto cartDto = this.cartService.getCartAll(principal.getName());
        return new ResponseEntity<>(cartDto, HttpStatus.ACCEPTED);
    }


    @GetMapping("/{cartId}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<CartDto>getCartById(@PathVariable  int cartId){

        System.out.println(cartId);
        CartDto cartByID = this.cartService.getCartByID(cartId);
        return new ResponseEntity<CartDto>(cartByID,HttpStatus.OK);
    }

    @DeleteMapping({"/{pid}"})
    @PreAuthorize("hasRole('User')")
    public ResponseEntity<CartDto> deleteCartItemFromCart(@PathVariable int pid,Principal p){

        CartDto remove = this.cartService.removeCartItemFromCart(p.getName(),pid);
        return new ResponseEntity<CartDto>(remove,HttpStatus.UPGRADE_REQUIRED);
    }





}
