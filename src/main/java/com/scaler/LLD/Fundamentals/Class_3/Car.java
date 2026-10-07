package com.scaler.LLD.Fundamentals.Class_3;

/**
 * LLD 3 — OOP-2: Car — Child (Sub) Class Extending Vehicle
 *
 * INHERITANCE = "IS-A" RELATIONSHIP:
 *   Car IS-A Vehicle.
 *   Car inherits all of Vehicle's fields and methods.
 *   Car adds its own field (musicSystem) and method (turnOnAC).
 *
 * WHAT Car GETS FROM Vehicle (without writing any of it):
 *   - Fields: vehicleNumber (private — in memory but not directly accessible),
 *             name, wheels, fuelCapacity
 *   - Methods: startVehicle(), refillFuel(), getVehicleNumber(), setVehicleNumber()
 *
 * CONSTRUCTOR CHAINING:
 *   When `new Car()` is called, Java implicitly inserts `super()` as the
 *   first line of Car's constructor → Vehicle's constructor runs first.
 *
 *   new Car()
 *       ↓
 *   Object()       ← every class extends Object
 *       ↓
 *   Vehicle()      ← super() — parent constructor
 *       ↓
 *   Car()          ← child constructor
 */
public class Car extends Vehicle {

    String musicSystem;    // Car-specific field

    /**
     * Car's no-arg constructor.
     *
     * Java inserts `super();` here automatically:
     *   public Car() {
     *       super();   ← implicit, calls Vehicle()
     *       System.out.println("Inside Car's Constructor");
     *   }
     */
    public Car() {
        // super();  ← Java does this implicitly
        System.out.println("  >> Inside Car's Constructor");
    }

    /** Car-specific behavior. */
    public void turnOnAC() {
        System.out.println("Turned On AC for " + name);
    }

    @Override
    public String toString() {
        return "Car{name='" + name + "'"
                + ", wheels=" + wheels
                + ", fuel=" + fuelCapacity
                + ", music='" + musicSystem + "'"
                + ", vehicleNo=" + getVehicleNumber() // private — must use getter
                + "}";
    }
}
