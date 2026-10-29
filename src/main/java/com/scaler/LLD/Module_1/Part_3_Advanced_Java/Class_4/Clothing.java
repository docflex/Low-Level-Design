package com.scaler.LLD.Module_1.Part_3_Advanced_Java.Class_4;

public class Clothing extends Item {
    private final String size;

    public Clothing(String id, String name, double price, int quantity, String size) {
        super(id, name, price, quantity);
        this.size = size;
    }

    public String getSize() {
        return size;
    }
}
