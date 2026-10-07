package com.scaler.LLD.Fundamentals.Class_4.vehicles;

/**
 * LLD 4 — OOP-3: Car — Extends Abstract Vehicle
 *
 * Demonstrates:
 *   - Method overriding (@Override calculateFare, startEngine)
 *   - Field hiding (String name = "Car" hides Vehicle.name = "Vehicle")
 *   - Child-specific method (turnOnMusic) — not callable via Vehicle reference
 */
public class Car extends Vehicle {

    // Field hiding: this "name" hides Vehicle's "name".
    // Variables are resolved by REFERENCE type at compile time.
    String name = "Car";

    @Override
    public double calculateFare(int kilometers) {
        return 10.0 * kilometers;    // Rs 10 per km
    }

    @Override
    public void startEngine() {
        System.out.println("Engine has started for Car: " + name);
    }

    /** Car-specific method — NOT in Vehicle, so not callable via Vehicle reference. */
    public void turnOnMusic() {
        System.out.println("Music is turned on");
    }
}
