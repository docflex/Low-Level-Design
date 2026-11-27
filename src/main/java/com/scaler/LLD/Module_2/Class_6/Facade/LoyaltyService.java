package com.scaler.LLD.Module_2.Class_6.Facade;

public class LoyaltyService implements PlaceOrderSubscriber {
    public void updateLoyalty(String userId) {
        System.out.println("  [Loyalty]   Updated loyalty points for " + userId);
    }

    public LoyaltyService(Amazon amazon) {
        amazon.subscribe(this);
    }

    @Override
    public void onOrderPlaced(String userId) {
        updateLoyalty(userId);
    }
}
