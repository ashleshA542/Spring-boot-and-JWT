package com.jwt.project.dto;
public class ProductDto {
    private int product_id;
    private String product_name;
    private double product_price;

    private boolean stock;
    private boolean live;
    private int product_quantity;






    private SupplierDto supplier;

    public SupplierDto getSupplier() {
        return supplier;
    }
    public void setSupplier(SupplierDto supplier) {
        this.supplier = supplier;
    }









    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public double getProduct_price() {
        return product_price;
    }

    public void setProduct_price(double product_price) {
        this.product_price = product_price;
    }

    public boolean isStock() {
        return stock;
    }

    public void setStock(boolean stock) {
        this.stock = stock;
    }

    public boolean isLive() {
        return live;
    }

    public void setLive(boolean live) {
        this.live = live;
    }

    public int getProduct_quantity() {
        return product_quantity;
    }

    public void setProduct_quantity(int product_quantity) {
        this.product_quantity = product_quantity;
    }

    public ProductDto(){
        super();
    }

    public ProductDto(int product_id, String product_name, double product_price, boolean stock, boolean live, int product_quantity) {
        this.product_id = product_id;
        this.product_name = product_name;
        this.product_price = product_price;
        this.stock = stock;
        this.live = live;
        this.product_quantity = product_quantity;
    }
}
