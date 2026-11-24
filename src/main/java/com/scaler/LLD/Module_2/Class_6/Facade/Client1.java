package com.scaler.LLD.Module_2.Class_6.Facade;

/** One client of the facade -- e.g. the website checkout. */
public class Client1 {
    public static void main(String[] args) {
        Amazon amazon = new Amazon();
        System.out.println("Website checkout:");
        amazon.placeOrder("ORD-1001", "Rehber", "ITEM-IPHONE", "iPhone: 1");
    }
}
