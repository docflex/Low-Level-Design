package com.scaler.LLD.Module_2.Class_6.Facade;

public class RecommendationService implements PlaceOrderSubscriber {
    public void updateRecommendations(String userId) {
        System.out.println("  [Recommend] Updated recommendations for " + userId);
    }

    public RecommendationService(Amazon amazon) {
        amazon.subscribe(this);
    }

    @Override
    public void onOrderPlaced(String userId) {
        updateRecommendations(userId);
    }
}
