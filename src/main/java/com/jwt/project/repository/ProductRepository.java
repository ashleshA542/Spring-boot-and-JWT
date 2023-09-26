package com.jwt.project.repository;

import com.jwt.project.entity.Product;
import com.jwt.project.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {
    List<Product> findBySupplier(Supplier supplier);


}
