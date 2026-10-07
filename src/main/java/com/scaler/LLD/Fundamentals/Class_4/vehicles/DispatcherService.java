package com.scaler.LLD.Fundamentals.Class_4.vehicles;

import java.util.ArrayList;
import java.util.List;

/**
 * LLD 4 — OOP-3: DispatcherService — The Power of Runtime Polymorphism
 *
 * This class shows WHY runtime polymorphism matters:
 *
 *   WITHOUT polymorphism (bad):
 *     for (Vehicle v : fleet) {
 *         if (v instanceof Car)        ((Car) v).calculateCarFare(km);
 *         else if (v instanceof Bike)  ((Bike) v).calculateBikeFare(km);
 *         else if (v instanceof Helicopter) ...
 *         // Every new vehicle type → change this method.
 *     }
 *
 *   WITH polymorphism (good):
 *     for (Vehicle v : fleet) {
 *         v.calculateFare(km);   // Java dispatches to the right child at runtime
 *     }
 *     // New vehicle type? Just create the class. Zero changes here.
 *
 * SCRIPT CORRECTIONS:
 *   1. Original constructor took List<Vehicle> but ignored it (bug).
 *      Fixed: no-arg constructor that initializes an empty list.
 *   2. Original client accessed ds.fleet directly (breaks encapsulation).
 *      Fixed: addVehicle() method for adding.
 */
public class DispatcherService {

    private final List<Vehicle> fleet;

    public DispatcherService() {
        this.fleet = new ArrayList<>();
    }

    public void addVehicle(Vehicle v) {
        fleet.add(v);
    }

    /**
     * Print fare estimates for all vehicles in the fleet.
     *
     * KEY INSIGHT: this method doesn't know or care whether v is a Car,
     * Bike, or Helicopter. It calls calculateFare() through the Vehicle
     * reference, and the JVM dispatches to the correct child implementation
     * at runtime (dynamic dispatch / virtual method invocation).
     */
    public void printPrices(int kilometers) {
        System.out.println("Fare estimates for " + kilometers + " km:");
        for (Vehicle v : fleet) {
            String type = v.getClass().getSimpleName();
            double fare = v.calculateFare(kilometers);
            System.out.printf("  %-12s → Rs %.2f%n", type, fare);
        }
    }
}
