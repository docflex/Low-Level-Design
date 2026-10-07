package com.scaler.LLD.Fundamentals.Class_4.vehicles;

/**
 * LLD 4 — OOP-3: Bike — Extends Abstract Vehicle
 *
 * @Override annotation is a defensive check:
 *   If someone accidentally renames this to "calculateFares()" (extra 's'),
 *   @Override forces a compile error — without it, Java silently creates
 *   a NEW method and the parent's logic runs instead. In Uber's case,
 *   that could mean charging the default fare instead of the bike fare.
 */
public class Bike extends Vehicle {

    @Override
    public double calculateFare(int kilometers) {
        return 5.0 * kilometers;    // Rs 5 per km
    }
}
