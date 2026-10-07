package com.scaler.LLD.Fundamentals.Class_3;

/**
 * LLD 3 — OOP-2: Encapsulation Demo + Copy Constructor + Constructor Chaining
 *
 * TEACHING FLOW:
 *   1. Show that private fields can't be set directly (encapsulation).
 *   2. Show setter validation rejecting bad input.
 *   3. Show read-only field (no setter for driverId).
 *   4. Demonstrate copy constructor creating a deep copy.
 *   5. Demonstrate constructor chaining via Driver(String, double).
 *   6. Show totalDriverCount tracking all objects.
 *
 * Run: Right-click → Run 'Client.main()'
 */
public class Client {
    public static void main(String[] args) {

        // ══════════════════════════════════════════════════════════════
        //  DEMO 1: Encapsulation — Private Fields Prevent Direct Access
        // ══════════════════════════════════════════════════════════════

        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.println("║  DEMO 1: Encapsulation — Private Fields          ║");
        System.out.println("╚══════════════════════════════════════════════════╝");

        Driver d1 = new Driver("ABC", 2.5, false);

        // d1.rating = -3.9;           // COMPILE ERROR! rating is private.
        // d1.driverId = 999;          // COMPILE ERROR! driverId is private AND final.

        // Must use the setter — which has validation
        d1.setRating(4.8);
        System.out.println("d1 after setRating(4.8): " + d1);

        // Try setting an invalid rating
        System.out.println("\nTrying setRating(-3.9)...");
        try {
            d1.setRating(-3.9);
        } catch (IllegalArgumentException e) {
            System.out.println("  REJECTED: " + e.getMessage());
        }

        // driverId is read-only (no setter exists)
        System.out.println("\ndriverId = " + d1.getDriverId() + " (read-only, no setter)");
        // d1.setDriverId(999);  // method doesn't exist — won't compile!


        // ══════════════════════════════════════════════════════════════
        //  DEMO 2: Copy Constructor — Deep Copy
        // ══════════════════════════════════════════════════════════════

        System.out.println("\n╔══════════════════════════════════════════════════╗");
        System.out.println("║  DEMO 2: Copy Constructor                       ║");
        System.out.println("╚══════════════════════════════════════════════════╝");

        // The tedious way (commented out):
        // Driver d2 = new Driver();
        // d2.setName(d1.getName());
        // d2.setRating(d1.getRating());
        // d2.setOnline(d1.isOnline());

        // The clean way — copy constructor:
        Driver d2 = new Driver(d1);

        System.out.println("d1 = " + d1);
        System.out.println("d2 = " + d2);
        System.out.println("d1 == d2 ? " + (d1 == d2));   // false — different objects!

        // The copy gets a FRESH driverId (not a duplicate of d1's ID)
        System.out.println("d1.id = " + d1.getDriverId() + ", d2.id = " + d2.getDriverId()
                + " (different IDs — copy is a distinct driver)");

        // Prove they're independent (deep copy)
        d2.setName("CHANGED");
        System.out.println("\nAfter d2.setName(\"CHANGED\"):");
        System.out.println("d1.name = " + d1.getName());   // still "ABC"
        System.out.println("d2.name = " + d2.getName());   // "CHANGED"


        // ══════════════════════════════════════════════════════════════
        //  DEMO 3: Constructor Chaining — this()
        // ══════════════════════════════════════════════════════════════

        System.out.println("\n╔══════════════════════════════════════════════════╗");
        System.out.println("║  DEMO 3: Constructor Chaining                   ║");
        System.out.println("╚══════════════════════════════════════════════════╝");

        // Driver(String, double) chains to Driver(String) via this(name)
        Driver d3 = new Driver("LOL", 3.8);
        System.out.println("d3 = " + d3);
        System.out.println("d3 has auto-assigned driverId = " + d3.getDriverId());


        // ══════════════════════════════════════════════════════════════
        //  DEMO 4: Static Variable — Total Count
        // ══════════════════════════════════════════════════════════════

        System.out.println("\n╔══════════════════════════════════════════════════╗");
        System.out.println("║  DEMO 4: Static — Total Driver Count            ║");
        System.out.println("╚══════════════════════════════════════════════════╝");

        // d1 (full), d2 (copy), d3 (chained) = 3 constructors called
        System.out.println("Total drivers created: " + Driver.totalDriverCount);
    }
}
