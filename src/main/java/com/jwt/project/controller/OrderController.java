package com.jwt.project.controller;

import com.jwt.project.dto.ApiResponse;
import com.jwt.project.dto.OrderDto;
import com.jwt.project.dto.OrderRequest;
import com.jwt.project.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping({"/api/order"})
public class OrderController {

    @Autowired
    private OrderService orderService;


    @PostMapping("/")
    @PreAuthorize("hasRole('User')")
    public ResponseEntity<OrderDto> createOrder(@RequestBody OrderRequest orderReq, Principal p) {
        String userName=p.getName();
        OrderDto order=this.orderService.orderCreate(orderReq,userName);
        return new ResponseEntity<OrderDto>(order, HttpStatus.CREATED);
    }


    @DeleteMapping("/{orderId}")
    @PreAuthorize("hasRole('User')")
    public ResponseEntity<?> cancelOrderById(@PathVariable int orderId){
        this.orderService.CancelOrder(orderId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Order deleted",true),HttpStatus.OK);
    }

    @GetMapping("/{orderId}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<OrderDto>findById(@PathVariable int orderId){
        OrderDto orderDto = this.orderService.findById(orderId);
        return new ResponseEntity<OrderDto>(orderDto,HttpStatus.ACCEPTED);
    }




}
