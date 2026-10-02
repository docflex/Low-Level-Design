package com.scaler.LLD.Fundamentals.Class_2;

/**
 * LLD 2 — OOP-1: Client — Creating and Using Objects
 *
 * This is the main demo runner for LLD 2. It walks through:
 *
 *   1. Object creation with different constructors
 *   2. Default values
 *   3. Manual field assignment & method calls
 *   4. Reference aliasing (two refs → same object)
 *   5. Setting to null & garbage collection
 *   6. Constructor overloading
 *   7. Static variable & method usage
 *   8. toString() in action
 *
 * MEMORY MODEL RECAP:
 *
 *   Driver d1 = new Driver();
 *   ──┬───  ─┬   ─┬─  ───┬──
 *     │      │    │      └── Constructor call (initializes the object)
 *     │      │    └── Allocates memory on the HEAP
 *     │      └── Reference variable (stored on the STACK)
 *     └── Data type
 *
 *   STACK:  d1 holds an ADDRESS (e.g. 0xABC) pointing to the heap object.
 *   HEAP:   The actual Driver object with all its field values.
 *
 * Run: Right-click → Run 'Client.main()'
 */
public class Client {

    public static void main(String[] args) {

        // ══════════════════════════════════════════════════════════════
        //  DEMO 1: Object Creation & Default Values
        // ══════════════════════════════════════════════════════════════

        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.println("║  DEMO 1: Object Creation & Default Values       ║");
        System.out.println("╚══════════════════════════════════════════════════╝");

        Driver d1 = new Driver();   // no-arg constructor → Java defaults

        // Java default values:
        //   int → 0, double → 0.0, boolean → false, String → null
        System.out.println("d1.driverId = " + d1.driverId);   // 0
        System.out.println("d1.name     = " + d1.name);       // null
        System.out.println("d1.rating   = " + d1.rating);     // 0.0
        System.out.println("d1.isOnline = " + d1.isOnline);   // false

        // Manually setting state
        d1.driverId = 1;
        d1.name = "Rehber";
        d1.rating = 4.3;
        d1.isOnline = true;

        System.out.println("\nAfter assignment:");
        System.out.println(d1);   // Uses our overridden toString()

        // Calling behaviors
        d1.acceptRide("RIDE-123");
        d1.changeOnlineStatus();   // toggles to OFFLINE


        // ══════════════════════════════════════════════════════════════
        //  DEMO 2: Reference Aliasing
        // ══════════════════════════════════════════════════════════════

        System.out.println("\n╔══════════════════════════════════════════════════╗");
        System.out.println("║  DEMO 2: Reference Aliasing                     ║");
        System.out.println("╚══════════════════════════════════════════════════╝");

        //  STACK                        HEAP
        //  ┌──────────────┐             ┌──────────────────────┐
        //  │ d1 = 0xABC ──┼──────┐      │ Driver Object        │
        //  └──────────────┘      ├─────>│  name = "Rehber"     │
        //  ┌──────────────┐      │      └──────────────────────┘
        //  │ d2 = 0xABC ──┼──────┘
        //  └──────────────┘
        //
        //  Both d1 and d2 point to the SAME object.
        //  Mutation through d2 is visible through d1 (and vice versa).

        Driver d2 = d1;   // d2 is an ALIAS — same object, NOT a copy

        System.out.println("d2.name       = " + d2.name);     // "Rehber"

        d2.name = "Varun";
        System.out.println("d1.name       = " + d1.name);     // "Varun" — same object!
        System.out.println("d1 == d2      = " + (d1 == d2));  // true — same reference


        // ══════════════════════════════════════════════════════════════
        //  DEMO 3: null & Garbage Collection
        // ══════════════════════════════════════════════════════════════

        System.out.println("\n╔══════════════════════════════════════════════════╗");
        System.out.println("║  DEMO 3: null & Garbage Collection              ║");
        System.out.println("╚══════════════════════════════════════════════════╝");

        // Setting d1 to null removes d1's reference.
        // But d2 still points to the object → NOT garbage collected yet.
        d1 = null;
        System.out.println("d1 set to null.");
        System.out.println("d2.name = " + d2.name);   // "Varun" — still reachable via d2

        // If we ALSO set d2 = null, the Driver object becomes unreachable
        // and eligible for GC. (Uncomment to test.)
        // d2 = null;


        // ══════════════════════════════════════════════════════════════
        //  DEMO 4: Constructor Overloading
        // ══════════════════════════════════════════════════════════════

        System.out.println("\n╔══════════════════════════════════════════════════╗");
        System.out.println("║  DEMO 4: Constructor Overloading                ║");
        System.out.println("╚══════════════════════════════════════════════════╝");

        Driver d3 = new Driver();                              // no-arg
        Driver d4 = new Driver("Sample");                      // name only
        Driver d5 = new Driver(1, "Rehber", 5.0, true);       // all fields

        System.out.println("d3 = " + d3);
        // Driver{id=0, name='null', rating=0.0, online=false}

        System.out.println("d4 = " + d4);
        // Driver{id=0, name='Sample', rating=0.0, online=false}

        System.out.println("d5 = " + d5);
        // Driver{id=1, name='Rehber', rating=5.0, online=true}


        // ══════════════════════════════════════════════════════════════
        //  DEMO 5: Static Variable & Static Method
        // ══════════════════════════════════════════════════════════════

        System.out.println("\n╔══════════════════════════════════════════════════╗");
        System.out.println("║  DEMO 5: Static Variable & Static Method        ║");
        System.out.println("╚══════════════════════════════════════════════════╝");

        // Access via CLASS NAME — not via an object reference.
        // Driver.totalDriverCount   ← correct
        // d1.totalDriverCount       ← works but bad practice (IDE warning)

        // How many Driver objects were created?
        // d1 (no-arg), d3 (no-arg), d4 (name), d5 (full) = 4 constructor calls
        // d2 = d1 was aliasing, NOT a new object — no constructor called.
        System.out.println("Total drivers created: " + Driver.totalDriverCount);

        // Static method — called on the class, no object needed
        Driver.register();


        // ══════════════════════════════════════════════════════════════
        //  DEMO 6: toString() Comparison
        // ══════════════════════════════════════════════════════════════

        System.out.println("\n╔══════════════════════════════════════════════════╗");
        System.out.println("║  DEMO 6: toString()                             ║");
        System.out.println("╚══════════════════════════════════════════════════╝");

        // Without our override, this would print "Driver@75bd9247"
        // With our override, it prints the meaningful field values.
        System.out.println("d5.toString() = " + d5.toString());
        System.out.println("println(d5)   = " + d5);   // println calls toString() implicitly

        // Compare: a plain Object (no override)
        Object obj = new Object();
        System.out.println("Plain Object  = " + obj);  // java.lang.Object@<hashcode>
    }
}
