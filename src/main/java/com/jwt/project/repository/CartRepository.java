package com.jwt.project.repository;

import com.jwt.project.entity.Cart;
import com.jwt.project.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart,Integer> {




}
