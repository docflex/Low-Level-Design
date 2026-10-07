package com.scaler.LLD.Fundamentals.Class_4.birds;

import java.util.List;

/**
 * LLD 4 — OOP-3: Class Explosion Prevention Demo (Bird Domain)
 *
 * TEACHING FLOW:
 *   1. Show the "class explosion" problem: N behaviors → 2^N classes.
 *   2. Show how interfaces solve it: each class picks only what it needs.
 *   3. flyAllBirds(List<Flyable>) — type safety prevents Penguin from sneaking in.
 *
 * See also: users/InterfacesDemo for the Uber HumanUser / Drivable example.
 *
 * Run: Right-click → Run 'ClassExplosionDemo.main()'
 */
public class ClassExplosionDemo {

    public static void main(String[] args) {

        // ══════════════════════════════════════════════════════════════
        //  DEMO 1: The Class Explosion Problem
        // ══════════════════════════════════════════════════════════════

        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.println("║  DEMO 1: Class Explosion WITHOUT Interfaces    ║");
        System.out.println("╚══════════════════════════════════════════════════╝");

        System.out.println("WITHOUT interfaces:");
        System.out.println("  Bird");
        System.out.println("    ├── FlyableDancingBird");
        System.out.println("    ├── FlyableNonDancingBird");
        System.out.println("    ├── NonFlyableDancingBird");
        System.out.println("    └── NonFlyableNonDancingBird");
        System.out.println("  2 behaviors -> 4 classes. N behaviors -> 2^N classes!");
        System.out.println();
        System.out.println("WITH interfaces:");
        System.out.println("  Dove    extends Bird implements Flyable");
        System.out.println("  Pigeon  extends Bird implements Flyable, Danceable");
        System.out.println("  Penguin extends Bird implements Danceable");
        System.out.println("  Each class implements only what it needs. No explosion.");


        // ══════════════════════════════════════════════════════════════
        //  DEMO 2: Each Bird's Capabilities
        // ══════════════════════════════════════════════════════════════

        System.out.println("\n╔══════════════════════════════════════════════════╗");
        System.out.println("║  DEMO 2: Each Bird's Capabilities              ║");
        System.out.println("╚══════════════════════════════════════════════════╝");

        Dove dove = new Dove();
        Pigeon pigeon = new Pigeon();
        Penguin penguin = new Penguin();

        System.out.println("Dove (Flyable only):");
        dove.fly();

        System.out.println("\nPigeon (Flyable + Danceable):");
        pigeon.fly();
        pigeon.dance();

        System.out.println("\nPenguin (Danceable only):");
        penguin.dance();
        // penguin.fly();  // COMPILE ERROR — Penguin doesn't implement Flyable


        // ══════════════════════════════════════════════════════════════
        //  DEMO 3: Type-Safe flyAllBirds(List<Flyable>)
        // ══════════════════════════════════════════════════════════════

        System.out.println("\n╔══════════════════════════════════════════════════╗");
        System.out.println("║  DEMO 3: Type-Safe flyAllBirds(List<Flyable>)  ║");
        System.out.println("╚══════════════════════════════════════════════════╝");

        // Only Flyable birds can be in this list.
        // Penguin doesn't implement Flyable → can't be added. Compile-time safety!
        List<Flyable> flyingBirds = List.of(dove, pigeon);
        // List<Flyable> bad = List.of(dove, penguin);  // COMPILE ERROR!

        flyAllBirds(flyingBirds);

        System.out.println();
        System.out.println("Penguin can't sneak into List<Flyable> — the compiler");
        System.out.println("catches it. Better than throwing an exception at runtime.");


        // ══════════════════════════════════════════════════════════════
        //  BAD DESIGN: fly() in abstract Bird
        // ══════════════════════════════════════════════════════════════

        System.out.println("\n╔══════════════════════════════════════════════════╗");
        System.out.println("║  Why NOT put fly() in abstract Bird?           ║");
        System.out.println("╚══════════════════════════════════════════════════╝");

        System.out.println("If fly() were in abstract Bird:");
        System.out.println("  - Penguin would be FORCED to implement fly()");
        System.out.println("  - It would have to throw an exception or do nothing");
        System.out.println("  - flyAllBirds(List<Bird>) would accept Penguin");
        System.out.println("  - Bug discovered at RUNTIME instead of COMPILE TIME");
        System.out.println();
        System.out.println("With Flyable interface:");
        System.out.println("  - Penguin simply doesn't implement Flyable");
        System.out.println("  - flyAllBirds(List<Flyable>) rejects Penguin at compile time");
        System.out.println("  - The type system IS the safety net.");
    }

    /**
     * Accepts ONLY Flyable things — Penguin can never get here.
     *
     * SCRIPT CORRECTION: The script used "Bird b" in the loop with List<Flyable>.
     * Fixed: loop variable is Flyable f (matches the list type).
     */
    static void flyAllBirds(List<Flyable> birds) {
        System.out.println("Flying all birds:");
        for (Flyable f : birds) {
            f.fly();
        }
    }
}
