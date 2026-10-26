package com.scaler.LLD.Module_1.Part_3_Advanced_Java.Class_2;

public class Electronics extends Item {
    private int warranty;


    public Electronics(String id, String name, double price, int quantity, int warranty) {
        super(id, name, price, quantity);
        this.warranty = warranty;
    }
}
