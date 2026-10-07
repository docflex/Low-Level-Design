package com.scaler.LLD.Fundamentals.Class_3;

/**
 * LLD 3 — OOP-2: Inheritance Demo
 *
 * TEACHING FLOW:
 *   1. Create a Car — observe constructor chaining (Vehicle → Car).
 *   2. Use inherited fields (name, wheels) and methods (startVehicle, refillFuel).
 *   3. Use Car-specific field (musicSystem) and method (turnOnAC).
 *   4. Show that private fields (vehicleNumber) need getter/setter.
 *   5. Print the full object to see all fields.
 *
 * MEMORY MODEL:
 *   A single Car object on the heap contains ALL fields:
 *
 *   ┌─────────────────────────────────┐
 *   │  Car object (on heap)           │
 *   ├─────────────────────────────────┤
 *   │  From Vehicle:                  │
 *   │    vehicleNumber = 42 (private) │
 *   │    name = "Maruti Suzuki"       │
 *   │    wheels = 4                   │
 *   │    fuelCapacity = 50            │
 *   ├─────────────────────────────────┤
 *   │  From Car:                      │
 *   │    musicSystem = "Bose"         │
 *   └─────────────────────────────────┘
 *
 * Run: Right-click → Run 'InheritanceDemo.main()'
 */
public class InheritanceDemo {

    public static void main(String[] args) {

        // ══════════════════════════════════════════════════════════════
        //  DEMO 1: Constructor Chaining — Vehicle() runs before Car()
        // ══════════════════════════════════════════════════════════════

        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.println("║  DEMO 1: Constructor Chaining                  ║");
        System.out.println("╚══════════════════════════════════════════════════╝");

        System.out.println("Creating new Car()...");
        Car c = new Car();
        // Output:
        //   >> Inside Vehicle's Constructor    ← runs FIRST (super())
        //   >> Inside Car's Constructor        ← runs SECOND


        // ══════════════════════════════════════════════════════════════
        //  DEMO 2: Using Inherited Fields & Methods
        // ══════════════════════════════════════════════════════════════

        System.out.println("\n╔══════════════════════════════════════════════════╗");
        System.out.println("║  DEMO 2: Inherited + Own Fields & Methods      ║");
        System.out.println("╚══════════════════════════════════════════════════╝");

        // Inherited from Vehicle (no code written in Car for these).
        // These are default-access fields — set directly here to keep
        // the inheritance demo simple. vehicleNumber (private) requires
        // getters/setters, shown in Demo 3 below.
        c.name = "Maruti Suzuki";
        c.wheels = 4;
        c.fuelCapacity = 50;

        // Car's own field
        c.musicSystem = "Bose";

        // Inherited methods
        c.startVehicle();           // "Vehicle is Starting: Maruti Suzuki"
        c.refillFuel(60);           // "Refilling Fuel to Capacity: 60"

        // Car's own method
        c.turnOnAC();               // "Turned On AC for Maruti Suzuki"


        // ══════════════════════════════════════════════════════════════
        //  DEMO 3: Private Fields — In Memory but Not Directly Accessible
        // ══════════════════════════════════════════════════════════════

        System.out.println("\n╔══════════════════════════════════════════════════╗");
        System.out.println("║  DEMO 3: Private Field via Getter/Setter       ║");
        System.out.println("╚══════════════════════════════════════════════════╝");

        // c.vehicleNumber = 42;    // COMPILE ERROR! private in Vehicle.
        c.setVehicleNumber(42);     // Must use the setter
        System.out.println("vehicleNumber = " + c.getVehicleNumber());

        // The field IS in memory (debugger shows it), just not directly accessible.
        System.out.println("\nFull Car object: " + c);


        // ══════════════════════════════════════════════════════════════
        //  DEMO 4: Inheritance = Code Reuse (DRY)
        // ══════════════════════════════════════════════════════════════

        System.out.println("\n╔══════════════════════════════════════════════════╗");
        System.out.println("║  DEMO 4: DRY — No Duplicate Code              ║");
        System.out.println("╚══════════════════════════════════════════════════╝");

        System.out.println("Car class defines: musicSystem, turnOnAC()");
        System.out.println("Everything else (name, wheels, fuelCapacity,");
        System.out.println("  startVehicle, refillFuel) is INHERITED from Vehicle.");
        System.out.println("Zero duplication. Change Vehicle → Car gets it for free.");
    }
}
