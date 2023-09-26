package com.jwt.project.entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int supplier_id;

    private String supplier_name;





    //many to one ma chai list use hunna but one to many ra many to many ma cha list use huncha but optional
    @OneToMany(mappedBy = "supplier",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<Product> product;




    public List<Product> getProduct() {
        return product;
    }

    public void setProduct(List<Product> product) {
        this.product = product;
    }

    public  Supplier(){
        super();
    }
    public Supplier(int supplier_id, String supplier_name) {
        this.supplier_id = supplier_id;
        this.supplier_name = supplier_name;
    }

    public int getSupplier_id() {
        return supplier_id;
    }

    public void setSupplier_id(int supplier_id) {
        this.supplier_id = supplier_id;
    }

    public String getSupplier_name() {
        return supplier_name;
    }

    public void setSupplier_name(String supplier_name) {
        this.supplier_name = supplier_name;
    }

}


