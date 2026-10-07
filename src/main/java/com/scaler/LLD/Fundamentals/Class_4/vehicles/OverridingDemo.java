package com.scaler.LLD.Fundamentals.Class_4.vehicles;

/**
 * LLD 4 — OOP-3: Runtime Polymorphism (Method Overriding) Demo
 *
 * TEACHING FLOW:
 *   1. Basic overriding: Car/Bike override Vehicle.calculateFare().
 *   2. Parent ref = new Child() — why it's allowed, and what it means.
 *   3. Methods → resolved by OBJECT (runtime).  Variables → resolved by REFERENCE (compile-time).
 *   4. DispatcherService — the super power of runtime polymorphism.
 *   5. Conditions for valid overriding.
 *
 * TWO-PART BINDING RULE:
 *   ┌──────────────────────────────────────────────────────────┐
 *   │  Vehicle v = new Car();                                  │
 *   │  ────────    ─────────                                   │
 *   │  Reference   Object                                      │
 *   │  (compile)   (runtime)                                   │
 *   │                                                          │
 *   │  WHICH methods you CAN call  → decided by reference type │
 *   │  WHICH implementation runs   → decided by object type    │
 *   │  WHICH field value you see   → decided by reference type │
 *   └──────────────────────────────────────────────────────────┘
 *
 * Run: Right-click → Run 'OverridingDemo.main()'
 */
public class OverridingDemo {

    public static void main(String[] args) {

        // ══════════════════════════════════════════════════════════════
        //  DEMO 1: Basic Method Overriding
        // ══════════════════════════════════════════════════════════════

        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.println("║  DEMO 1: Basic Overriding                      ║");
        System.out.println("╚══════════════════════════════════════════════════╝");

        Car car = new Car();
        Bike bike = new Bike();

        System.out.println("Car  fare for 12 km: Rs " + car.calculateFare(12));    // 120.0
        System.out.println("Bike fare for 12 km: Rs " + bike.calculateFare(12));   // 60.0

        // Both call calculateFare(12) — same signature, different behavior.
        // The child's implementation replaces ("overrides") the parent's.


        // ══════════════════════════════════════════════════════════════
        //  DEMO 2: Parent ref = new Child()
        // ══════════════════════════════════════════════════════════════

        System.out.println("\n╔══════════════════════════════════════════════════╗");
        System.out.println("║  DEMO 2: Parent ref = new Child()              ║");
        System.out.println("╚══════════════════════════════════════════════════╝");

        Vehicle v = new Car();      // Parent ref, child object — ALLOWED
        // Car c = new Vehicle();   // COMPILE ERROR: child ref, parent object

        v.startEngine();
        // Calls Car's startEngine() → "Engine has started for Car: Car"
        // The OBJECT (Car) decides which implementation runs.

        // v.turnOnMusic();  // COMPILE ERROR!
        // The REFERENCE (Vehicle) decides what methods you CAN call.
        // turnOnMusic() doesn't exist in Vehicle → compiler rejects it.
        System.out.println("v.turnOnMusic() → COMPILE ERROR (not in Vehicle)");


        // ══════════════════════════════════════════════════════════════
        //  DEMO 3: Variables — Resolved by Reference (Field Hiding)
        // ══════════════════════════════════════════════════════════════

        System.out.println("\n╔══════════════════════════════════════════════════╗");
        System.out.println("║  DEMO 3: Variable Binding (Field Hiding)       ║");
        System.out.println("╚══════════════════════════════════════════════════╝");

        // Vehicle has: String name = "Vehicle"
        // Car    has: String name = "Car"    (hides Vehicle's name)

        System.out.println("v.name         = " + v.name);
        // Prints "Vehicle"! Even though the object is a Car.
        // Variables are bound at COMPILE TIME by the REFERENCE type.

        System.out.println("((Car) v).name = " + ((Car) v).name);
        // Prints "Car" — we cast the reference to Car, so we see Car's field.

        System.out.println();
        System.out.println("KEY RULE:");
        System.out.println("  -> Variables are decided by the REFERENCE (compile-time)");
        System.out.println("  -> Methods  are decided by the OBJECT    (runtime)");
        System.out.println();
        System.out.println("'Variable overriding' does NOT exist in Java.");
        System.out.println("What looks like overriding is actually 'field hiding.'");


        // ══════════════════════════════════════════════════════════════
        //  DEMO 4: DispatcherService — The Super Power
        // ══════════════════════════════════════════════════════════════

        System.out.println("\n╔══════════════════════════════════════════════════╗");
        System.out.println("║  DEMO 4: DispatcherService — Polymorphism Power║");
        System.out.println("╚══════════════════════════════════════════════════╝");

        DispatcherService ds = new DispatcherService();
        ds.addVehicle(new Car());
        ds.addVehicle(new Bike());
        ds.addVehicle(new Helicopter());

        ds.printPrices(10);
        // The loop calls v.calculateFare(10) on each — Java dispatches
        // to Car's, Bike's, or Helicopter's implementation at runtime.

        System.out.println();
        System.out.println("Adding Helicopter required ZERO changes to DispatcherService.");
        System.out.println("Just create the class, implement calculateFare(), and add it.");


        // ══════════════════════════════════════════════════════════════
        //  DEMO 5: Overriding Rules Summary
        // ══════════════════════════════════════════════════════════════

        System.out.println("\n╔══════════════════════════════════════════════════╗");
        System.out.println("║  DEMO 5: Method Overriding Conditions          ║");
        System.out.println("╚══════════════════════════════════════════════════╝");

        System.out.println("For a valid override, the child method must satisfy:");
        System.out.println();
        System.out.println("  1. SAME SIGNATURE (name + parameter types)");
        System.out.println();
        System.out.println("  2. RETURN TYPE: same or a subtype (covariant return)");
        System.out.println("     e.g., parent returns Object -> child can return String");
        System.out.println("     (This is a standalone Java 5 feature, NOT related to generics.)");
        System.out.println();
        System.out.println("  3. ACCESS MODIFIER: same or MORE visible (widen, not narrow)");
        System.out.println("     protected in parent -> public in child    (widened  = OK)");
        System.out.println("     public in parent    -> protected in child (narrowed = ERROR)");
        System.out.println();
        System.out.println("  4. Use @Override annotation as a defensive check!");
        System.out.println("     Without it, a typo like calculateFares() silently");
        System.out.println("     creates a NEW method instead of overriding the old one.");
    }
}
