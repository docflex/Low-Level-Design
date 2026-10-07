package com.scaler.LLD.Fundamentals.Class_4.vehicles;

/**
 * LLD 4 — OOP-3: Vehicle — Abstract Class (Uber Domain)
 *
 * EVOLUTION (teaching flow):
 *   Step 1 (Overriding demo):  Vehicle was a concrete class with calculateFare().
 *   Step 2 (Abstraction demo): Vehicle is made abstract — no one can do "new Vehicle()".
 *   Step 3 (Abstract method):  calculateFare() becomes abstract — children MUST override it.
 *
 * WHY ABSTRACT?
 *   "Vehicle" is a concept, not a real-world object. You book a Car, Bike, or
 *   Helicopter — never a "Vehicle." Making it abstract prevents instantiation
 *   and forces subclasses to provide their own calculateFare() logic.
 *
 * ABSTRACT vs NON-ABSTRACT METHODS:
 *   - abstract calculateFare(): each vehicle type has different pricing.
 *     No sensible default → force the child to implement it.
 *   - concrete startEngine(): all vehicles start an engine the same way.
 *     Common behavior → provide a default implementation.
 *
 * FIELD HIDING (used in OverridingDemo):
 *   Vehicle has `String name = "Vehicle"`. Car also declares
 *   `String name = "Car"`. When accessed through a Vehicle reference,
 *   the REFERENCE type determines which field is read (compile-time binding).
 *   This is "field hiding," NOT overriding — variables are never overridden.
 */
public abstract class Vehicle {

    // Used in the field-hiding demo (OverridingDemo)
    String name = "Vehicle";

    // ── Abstract method: child MUST implement ──
    // Each vehicle type has a different fare formula.
    public abstract double calculateFare(int kilometers);

    // ── Concrete method: common behavior ──
    public void startEngine() {
        System.out.println("Engine has started for Vehicle: " + name);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{name='" + name + "'}";
    }
}
