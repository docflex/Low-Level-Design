package com.scaler.LLD.Module_2.Class_6.Facade;

/** A second client -- e.g. the mobile app. Same one-line call, no duplicated steps. */
public class Client2 {
    public static void main(String[] args) {
        Amazon amazon = new Amazon();
        System.out.println("Mobile app checkout:");
        amazon.placeOrder("ORD-1002", "Aman", "ITEM-KINDLE", "Kindle: 1");
    }
}
