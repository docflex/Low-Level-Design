package com.scaler.LLD.Module_2.Class_6.Facade;

/** Observer: a service that wants to know whenever an order is placed. */
public interface PlaceOrderSubscriber {
    void onOrderPlaced(String userId);
}
