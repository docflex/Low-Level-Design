package com.scaler.LLD.Fundamentals.Class_4.vehicles;

/**
 * LLD 4 — OOP-3: Helicopter — Extends Abstract Vehicle
 *
 * Adding Helicopter requires:
 *   1. Create this class and implement calculateFare().  ← that's it!
 *
 * WITHOUT runtime polymorphism you'd also need to:
 *   2. Write a calculateHelicopterFare() method.
 *   3. Add an `if (v instanceof Helicopter)` block in DispatcherService.
 *
 * Runtime polymorphism eliminates steps 2 and 3 entirely.
 */
public class Helicopter extends Vehicle {

    @Override
    public double calculateFare(int kilometers) {
        return 50.0 * kilometers;   // Rs 50 per km
    }
}
