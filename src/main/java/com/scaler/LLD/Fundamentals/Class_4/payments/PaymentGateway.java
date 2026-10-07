package com.scaler.LLD.Fundamentals.Class_4.payments;

/**
 * LLD 4 — OOP-3: PaymentGateway — Interface as Noun (the 1% case)
 *
 * Interfaces are 99% verbs (Drivable, Flyable, Rechargeable).
 * The 1% exception: when you want to decouple from a specific
 * implementation. PaymentGateway is a noun, but used as an interface
 * so TripService doesn't depend on a specific gateway (RazorPay, Paytm).
 *
 * This is called "programming to an interface" — one of the most
 * important OOP design principles. You'll learn it formally as the
 * Dependency Inversion Principle in SOLID (LLD Module 2).
 */
public interface PaymentGateway {

    void pay(double amount);

    String getGatewayName();
}
