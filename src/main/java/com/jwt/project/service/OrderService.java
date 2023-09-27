package com.jwt.project.service;

import com.jwt.project.dto.OrderDto;
import com.jwt.project.dto.OrderRequest;
import com.jwt.project.entity.*;
import com.jwt.project.exception.ResourceNotFoundException;
import com.jwt.project.repository.CartRepository;
import com.jwt.project.repository.OrderRepository;
import com.jwt.project.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private OrderRepository orderRepository;

    public OrderDto orderCreate(OrderRequest request, String userName) {

        User user = this.userRepository.findByUserName(userName).orElseThrow(()->new ResourceNotFoundException("User not found"));

        int cartId=request.getCartId();
        String orderAddress=request.getOrderAddress();
        //find cart
        Cart cart= this.cartRepository.findById(cartId).orElseThrow(()->new ResourceNotFoundException("Cart Not Found"));
        //getting CartItem
        Set<CartItem> items  = cart.getItems();
        Order order=new Order();

        AtomicReference<Double> totalOrderPrice= new AtomicReference<Double>(0.0);
        Set<OrderItem>	orderitems=items.stream().map((cartItem)-> {
            OrderItem orderItem=new OrderItem();
            //set cartItem into OrderItem

            //set product in orderItem
            orderItem.setProduct(cartItem.getProduct());

            //set productQty in orderItem

            orderItem.setProductQuantity(cartItem.getQuantity());

            orderItem.setTotalProductprice(cartItem.getTotalprice());
            orderItem.setOrder(order);

            totalOrderPrice.set(totalOrderPrice.get()+ orderItem.getTotalProductprice());
            int productId=orderItem.getProduct().getProduct_id();

            return orderItem;
        }).collect(Collectors.toSet());

        order.setBillingAddress(orderAddress);
        order.setOrderDelivered(null);
        order.setOrderStatus("CREATED");
        order.setPaymentStatus("NOT PAID");
        order.setUser(user);
        order.setOrderItem(orderitems);
        order.setOrderAmt(totalOrderPrice.get());
        order.setOrderCreateAt(new Date());
        Order save;
        if(order.getOrderAmt()>0){
            save = this.orderRepository.save(order);
            cart.getItems().clear();
            this.cartRepository.save(cart);
            System.out.println("Hello");
        }else {
            System.out.println(order.getOrderAmt());
            throw new ResourceNotFoundException("Plz Add Cart First and place Order");
        }
        return this.modelMapper.map(save,OrderDto.class);
    }



    public void CancelOrder(int orderId){
        Order order = this.orderRepository.findById(orderId).orElseThrow(()->new ResourceNotFoundException("Order not Found"));
        this.orderRepository.delete(order);
    }


    public OrderDto findById(int orderId){
        Order order = this.orderRepository.findById(orderId).orElseThrow(()->new ResourceNotFoundException("order not found"));
        return this.modelMapper.map(order,OrderDto.class);
    }





}
