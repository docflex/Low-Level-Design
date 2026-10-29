package com.scaler.LLD.Module_1.Part_3_Advanced_Java.Class_4;

public class Electronics extends Item {
    private final int warranty;

    public Electronics(String id, String name, double price, int quantity, int warranty) {
        super(id, name, price, quantity);
        this.warranty = warranty;
    }

    public int getWarranty() {
        return warranty;
    }
}
