package com.jwt.project.service;


import com.jwt.project.dto.CartDto;
import com.jwt.project.dto.ItemRequest;
import com.jwt.project.entity.Cart;
import com.jwt.project.entity.CartItem;
import com.jwt.project.entity.Product;
import com.jwt.project.entity.User;
import com.jwt.project.exception.ResourceNotFoundException;
import com.jwt.project.repository.CartRepository;
import com.jwt.project.repository.ProductRepository;
import com.jwt.project.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;


@Service
public class CartService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ModelMapper modelMapper;

    public CartDto addItem(ItemRequest item, String userName) {
        int productId = item.getProductId();
        int quantity = item.getQuantity();

        // Fetching user and product using username as primary key
        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        if (!product.isStock()) {
            throw new ResourceNotFoundException("Product Out of Stock");
        }

        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setQuantity(quantity);
        double totalprice = product.getProduct_price() * quantity;
        cartItem.setTotalprice(totalprice);

        Cart cart = user.getCart();

        /*if (cart == null) {
            cart = new Cart();
            cart.setUser(user);
        }*/


        if (cart == null) {
            cart = new Cart();
            cart.setUser(user);
            cart = cartRepository.save(cart); // Save the cart to the database
        }


        cartItem.setCart(cart);
        Set<CartItem> items = cart.getItems();

        AtomicReference<Boolean> flag = new AtomicReference<>(false);

        Set<CartItem> newproduct = items.stream().map((i) -> {
            if (i.getProduct().getProduct_id() == product.getProduct_id()) {
                i.setQuantity(quantity);
                i.setTotalprice(totalprice);
                flag.set(true);
            }
            return i;
        }).collect(Collectors.toSet());

        if (flag.get()) {
            items.clear();
            items.addAll(newproduct);
        } else {
            cartItem.setCart(cart);
            items.add(cartItem);
        }

        Cart saveCart = cartRepository.save(cart);

        return modelMapper.map(saveCart, CartDto.class);
    }


   /* public CartDto getcartAll(String userName){
        //find user
        User user = this.userRepository.findByUserName(userName).orElseThrow(()->new ResourceNotFoundException("User Not Found"));
        //find cart
        Cart cart= this.userRepository.findByUser(user).orElseThrow(()->new ResourceNotFoundException("There is no cart"));

        return this.modelMapper.map(cart,CartDto.class);

    }*/



    public CartDto getCartAll(String userName) {
        // Find user by userName
        User user = this.userRepository.findByUserName(userName)
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found"));

        // Find user's cart
        Cart cart = user.getCart();

        if (cart == null) {
            throw new ResourceNotFoundException("There is no cart for this user");
        }
        return this.modelMapper.map(cart, CartDto.class);
    }


    public CartDto getCartByID(int cartId){
        Cart findByUserAndcartId = this.cartRepository.findById(cartId).orElseThrow(()->new ResourceNotFoundException("Cart not Found"));
        return this.modelMapper.map(findByUserAndcartId,CartDto.class);
    }

    public CartDto removeCartItemFromCart(String userName, int ProductId){
        User user=this.userRepository.findByUserName(userName).orElseThrow(()->new ResourceNotFoundException("User Not found"));

        Cart cart=user.getCart();
        Set<CartItem> items = cart.getItems();

        boolean removeIf = items.removeIf((i)->i.getProduct().getProduct_id()==ProductId);
        Cart save = this.cartRepository.save(cart);
        System.out.println(removeIf);
        return this.modelMapper.map(save,CartDto.class);
    }




}
