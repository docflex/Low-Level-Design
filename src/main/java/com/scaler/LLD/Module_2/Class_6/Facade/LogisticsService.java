package com.scaler.LLD.Module_2.Class_6.Facade;

/** Subsystem: shipping. */
public class LogisticsService {
    public void initiateShipping(String orderId) {
        System.out.println("  [Logistics] Initiated shipping for order " + orderId);
    }
}
