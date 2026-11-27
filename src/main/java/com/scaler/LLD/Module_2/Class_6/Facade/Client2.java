package com.scaler.LLD.Module_2.Class_6.Facade;

/**
 * A second client -- e.g. the mobile app. Same one-line call, no duplicated steps.
 * Also shows the Observer pattern (Module 2, Class 8): loyalty and recommendations subscribe
 * to order events, and can be unsubscribed without touching the facade.
 */
public class Client2 {
    public static void main(String[] args) {
        Amazon amazon = new Amazon();

        LoyaltyService loyaltyService = new LoyaltyService(amazon);
        RecommendationService recommendationService = new RecommendationService(amazon);

        System.out.println("Mobile app checkout (loyalty + recommendations subscribed):");
        amazon.placeOrder("ORD-1002", "Aman", "ITEM-KINDLE", "Kindle: 1");

        amazon.unsubscribe(recommendationService);
        System.out.println("\nAfter recommendations unsubscribes:");
        amazon.placeOrder("ORD-1003", "Rehber", "ITEM-KINDLE", "Kindle: 1");
    }
}
