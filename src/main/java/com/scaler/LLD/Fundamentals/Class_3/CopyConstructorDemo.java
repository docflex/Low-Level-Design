package com.scaler.LLD.Fundamentals.Class_3;

/**
 * LLD 3 — OOP-2: Shallow vs Deep Copy Demo
 *
 * TEACHING FLOW:
 *   1. Shallow copy (aliasing) — two references, ONE object.
 *   2. Deep copy (copy constructor) — two references, TWO objects.
 *   3. The TRAP: nested objects can make a "copy constructor" shallow.
 *   4. The FIX: recursively deep-copy nested objects.
 *   5. Why Strings don't need deep copying (immutable).
 *
 * Run: Right-click → Run 'CopyConstructorDemo.main()'
 */
public class CopyConstructorDemo {

    public static void main(String[] args) {

        // ══════════════════════════════════════════════════════════════
        //  DEMO 1: Shallow Copy — Aliasing (DANGER)
        // ══════════════════════════════════════════════════════════════

        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.println("║  DEMO 1: Shallow Copy (Aliasing)               ║");
        System.out.println("╚══════════════════════════════════════════════════╝");

        Route r1 = new Route(1, "Delhi", "Mumbai");
        Route r2 = r1;    // r2 is an ALIAS — points to the SAME object

        System.out.println("r1 = " + r1);
        System.out.println("r2 = " + r2);
        System.out.println("r1 == r2 ? " + (r1 == r2));   // true — same object!

        //  ┌──────────────┐        ┌─────────────────────────┐
        //  │ r1 = 0xAAA ──┼──┐     │ Route{Delhi → Mumbai}   │
        //  └──────────────┘  ├────>│ (one object, two refs)  │
        //  ┌──────────────┐  │     └─────────────────────────┘
        //  │ r2 = 0xAAA ──┼──┘
        //  └──────────────┘

        r2.setSource("Chennai");   // mutates the SHARED object via setter
        System.out.println("\nAfter r2.setSource(\"Chennai\"):");
        System.out.println("r1 = " + r1);   // Chennai! r1 sees the change (DANGER)
        System.out.println("r2 = " + r2);   // same object


        // ══════════════════════════════════════════════════════════════
        //  DEMO 2: Deep Copy — Copy Constructor (SAFE)
        // ══════════════════════════════════════════════════════════════

        System.out.println("\n╔══════════════════════════════════════════════════╗");
        System.out.println("║  DEMO 2: Deep Copy (Copy Constructor)           ║");
        System.out.println("╚══════════════════════════════════════════════════╝");

        Route original = new Route(2, "Bangalore", "Hyderabad");
        Route copy = new Route(original);   // deep copy — NEW object

        System.out.println("original = " + original);
        System.out.println("copy     = " + copy);
        System.out.println("original == copy ? " + (original == copy));   // false!

        copy.setSource("Pune");   // only affects the copy
        System.out.println("\nAfter copy.setSource(\"Pune\"):");
        System.out.println("original = " + original);   // still Bangalore (SAFE)
        System.out.println("copy     = " + copy);       // Pune


        // ══════════════════════════════════════════════════════════════
        //  DEMO 3: The Nested Object Trap (Fare contains Route)
        // ══════════════════════════════════════════════════════════════

        System.out.println("\n╔══════════════════════════════════════════════════╗");
        System.out.println("║  DEMO 3: Nested Object — Deep Copy Done Right  ║");
        System.out.println("╚══════════════════════════════════════════════════╝");

        Route route = new Route(3, "Delhi", "Mumbai");
        Fare f1 = new Fare(1, 10_000, route);
        Fare f2 = new Fare(f1);   // copy constructor — deep copies the nested Route

        System.out.println("f1 = " + f1);
        System.out.println("f2 = " + f2);

        // Are the Fare objects different?
        System.out.println("\nf1 == f2 ? " + (f1 == f2));   // false — good!
        // Are the NESTED Route objects also different?
        System.out.println("f1.route == f2.route ? "
                + (f1.getRoute() == f2.getRoute()));         // false — deep!

        // Prove independence: mutate f2's route and amount
        f2.getRoute().setSource("Goa");
        f2.setAmount(5_000);

        System.out.println("\nAfter modifying f2:");
        System.out.println("f1 = " + f1);   // Delhi, 10000 — UNAFFECTED
        System.out.println("f2 = " + f2);   // Goa, 5000

        //  ┌──────┐     ┌─────────────────────┐     ┌───────────────────┐
        //  │  f1  ─┼───>│ Fare{10000}          │───>│ Route{Delhi->Mum} │
        //  └──────┘     └─────────────────────┘     └───────────────────┘
        //
        //  ┌──────┐     ┌─────────────────────┐     ┌───────────────────┐
        //  │  f2  ─┼───>│ Fare{5000}           │───>│ Route{Goa->Mum}   │
        //  └──────┘     └─────────────────────┘     └───────────────────┘
        //
        //  Completely independent! That's a proper deep copy.


        // ══════════════════════════════════════════════════════════════
        //  SUMMARY
        // ══════════════════════════════════════════════════════════════

        System.out.println("\n╔══════════════════════════════════════════════════╗");
        System.out.println("║  Copy Rules Summary                             ║");
        System.out.println("╚══════════════════════════════════════════════════╝");
        System.out.println("  Primitives (int, double):  always copied by value -> auto deep.");
        System.out.println("  Immutables (String):       sharing is safe -> no copy needed.");
        System.out.println("  Custom objects (Route):    must be manually deep-copied.");
        System.out.println("  Nested objects:            recursively deep-copy at every level.");
    }
}
