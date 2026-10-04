package com.scaler.LLD.Fundamentals.Class_3;

/**
 * LLD 3 — OOP-2: Vehicle — Parent (Super) Class for Inheritance Demo
 *
 * This class is the BASE of the inheritance hierarchy:
 *   Vehicle (parent)
 *     └── Car (child)
 *
 * KEY CONCEPTS:
 *   - vehicleNumber is PRIVATE: inherited in memory but not directly
 *     accessible by subclasses. Must use getVehicleNumber().
 *   - name, wheels, fuelCapacity are DEFAULT access (package-private):
 *     accessible within the same package.
 *   - The no-arg constructor prints a message to prove that the parent
 *     constructor runs BEFORE the child constructor (constructor chaining).
 *
 * PRIVATE + INHERITANCE:
 *   When Car extends Vehicle, the Car object in memory contains ALL
 *   Vehicle fields — including private ones. The debugger shows them.
 *   But Java's access control prevents Car from reading/writing
 *   vehicleNumber directly. It must go through the getter/setter.
 */
public class Vehicle {

    // private — memory allocated in Car objects, but NOT directly accessible
    private int vehicleNumber;

    // default (package-private) — accessible within same package.
    //
    // TEACHING NOTE: These are intentionally NOT private here so that
    // InheritanceDemo can show `c.name = "Maruti Suzuki"` directly,
    // making the inheritance concept clear without getter/setter noise.
    // In production code, these would follow the same encapsulation
    // pattern as Driver (private + getters/setters).
    // vehicleNumber above shows the "proper" encapsulated pattern.
    String name;
    int wheels;
    int fuelCapacity;

    /**
     * No-arg constructor.
     * Prints a message to demonstrate constructor chaining order.
     *
     * When `new Car()` is called:
     *   1. Java implicitly inserts super() → calls this Vehicle()
     *   2. Then Car's constructor body runs
     */
    public Vehicle() {
        System.out.println("  >> Inside Vehicle's Constructor");
    }

    // ── Behaviors ──

    public void startVehicle() {
        System.out.println("Vehicle is Starting: " + name);
    }

    public void refillFuel(int fuelCapacity) {
        System.out.println("Refilling Fuel to Capacity: " + fuelCapacity);
        this.fuelCapacity = fuelCapacity;
    }

    // ── Getter/Setter for private vehicleNumber ──
    // This is how subclasses (Car) access the private field.

    public int getVehicleNumber() { return vehicleNumber; }

    public void setVehicleNumber(int vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    @Override
    public String toString() {
        return "Vehicle{no=" + vehicleNumber
                + ", name='" + name + "'"
                + ", wheels=" + wheels
                + ", fuel=" + fuelCapacity + "}";
    }
}
