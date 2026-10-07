package com.scaler.LLD.Fundamentals.Class_4.payments;

/**
 * LLD 4 — OOP-3: TripService — Composition (HAS-A) Example
 *
 * TripService does NOT extend PaymentGateway. It HAS A PaymentGateway.
 * This is composition: one class uses/contains another.
 *
 *   Inheritance → IS-A:    Car IS A Vehicle
 *   Composition → HAS-A:   TripService HAS A PaymentGateway
 *
 * WHY COMPOSITION HERE?
 *   If TripService hard-coded RazorPayGateway, switching to Paytm
 *   would require rewriting TripService. Instead, we accept a
 *   PaymentGateway interface — the specific implementation is
 *   injected from outside.
 *
 * SCRIPT CORRECTION: Original used PaymentGateway.payMoney() (static call
 * on the interface). Fixed: uses the instance variable paymentGateway.pay().
 */
public class TripService {

    private final PaymentGateway paymentGateway;

    public TripService(PaymentGateway paymentGateway) {
        this.paymentGateway = paymentGateway;
    }

    public void completeTrip(double fare) {
        System.out.println("Trip completed. Processing payment via "
                + paymentGateway.getGatewayName() + "...");
        paymentGateway.pay(fare);
    }
}
