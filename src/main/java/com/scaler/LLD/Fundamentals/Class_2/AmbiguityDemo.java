package com.scaler.LLD.Fundamentals.Class_2;

/**
 * LLD 2 — OOP-1: The Ambiguity Problem — Why `this` is Essential
 *
 * When constructor parameter names match field names, Java resolves
 * BOTH sides to the parameter (nearest scope wins).
 *
 * This demo proves it: without `this`, the field is never assigned.
 *
 * SCRIPT CORRECTION:
 *   The original script says (line 469):
 *     "the driverId on the LEFT refers to the Class Variable
 *      and the LEFT one refers to the parameter."
 *   It says "left" twice (typo), AND the explanation is wrong.
 *
 *   Truth: In `driverId = driverId;` without `this`, BOTH sides
 *   resolve to the parameter. The assignment is a no-op
 *   (parameter = itself). The class field is NEVER touched.
 *
 * Run: Right-click → Run 'AmbiguityDemo.main()'
 */
public class AmbiguityDemo {

    // ══════════════════════════════════════════════════════════════════
    //  BUGGY CLASS — no `this` keyword
    // ══════════════════════════════════════════════════════════════════

    static class BuggyDriver {
        int driverId;
        String name;
        double rating;
        boolean isOnline;

        // BUG: all four assignments are no-ops!
        // Java resolves both sides to the PARAMETER (nearest scope).
        // The instance fields are never assigned.
        public BuggyDriver(int driverId, String name, double rating, boolean isOnline) {
            driverId = driverId;     // parameter = parameter (no-op)
            name = name;             // parameter = parameter (no-op)
            rating = rating;         // parameter = parameter (no-op)
            isOnline = isOnline;     // parameter = parameter (no-op)
        }

        @Override
        public String toString() {
            return "BuggyDriver{id=" + driverId + ", name='" + name
                    + "', rating=" + rating + ", online=" + isOnline + "}";
        }
    }


    // ══════════════════════════════════════════════════════════════════
    //  FIXED CLASS — with `this` keyword
    // ══════════════════════════════════════════════════════════════════

    static class FixedDriver {
        int driverId;
        String name;
        double rating;
        boolean isOnline;

        // CORRECT: `this.field = parameter` disambiguates the names.
        //
        //   this.driverId = driverId;
        //   ^^^^              ^^^^
        //   class field       constructor parameter
        //
        // `this` refers to the CURRENT OBJECT being constructed.
        public FixedDriver(int driverId, String name, double rating, boolean isOnline) {
            this.driverId = driverId;
            this.name = name;
            this.rating = rating;
            this.isOnline = isOnline;
        }

        @Override
        public String toString() {
            return "FixedDriver{id=" + driverId + ", name='" + name
                    + "', rating=" + rating + ", online=" + isOnline + "}";
        }
    }


    // ══════════════════════════════════════════════════════════════════
    //  DEMO
    // ══════════════════════════════════════════════════════════════════

    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.println("║  Ambiguity Demo — Why `this` Is Essential       ║");
        System.out.println("╚══════════════════════════════════════════════════╝");

        System.out.println("\n── WITHOUT `this` (BUGGY) ──");
        BuggyDriver buggy = new BuggyDriver(1, "Rehber", 5.0, true);
        System.out.println("Passed:   id=1, name='Rehber', rating=5.0, online=true");
        System.out.println("Got:      " + buggy);
        System.out.println("          ^ All defaults! Fields were NEVER assigned.");
        //
        //  Output:
        //    BuggyDriver{id=0, name='null', rating=0.0, online=false}
        //
        //  Because `driverId = driverId;` assigned the parameter to ITSELF.
        //  The class field `this.driverId` was never touched → stays at 0.

        System.out.println("\n── WITH `this` (FIXED) ──");
        FixedDriver fixed = new FixedDriver(1, "Rehber", 5.0, true);
        System.out.println("Passed:   id=1, name='Rehber', rating=5.0, online=true");
        System.out.println("Got:      " + fixed);
        System.out.println("          ^ Correct! `this.field = param` works.");
        //
        //  Output:
        //    FixedDriver{id=1, name='Rehber', rating=5.0, online=true}

        System.out.println("\n── SCOPE RULE ──");
        System.out.println("  Java resolves variable names to the NEAREST scope.");
        System.out.println("  Inside a constructor, parameter names shadow field names.");
        System.out.println("  `this.field` explicitly refers to the instance field.");
    }
}
