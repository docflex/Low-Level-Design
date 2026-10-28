package com.scaler.LLD.Module_1.Part_3_Advanced_Java.Class_2;

public class Order {
    private final String orderId;
    private final boolean isExpress;

    public Order(String orderId, boolean isExpress) {
        this.orderId = orderId;
        this.isExpress = isExpress;
    }

    public boolean isExpress() {
        return isExpress;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", isExpress=" + isExpress +
                '}';
    }
}
