package com.scaler.LLD.Fundamentals.Class_3;

import com.scaler.LLD.Fundamentals.Class_3.animals.Elephant;
import com.scaler.LLD.Fundamentals.Class_3.animals.baby.BabyElephant;

/**
 * LLD 3 — OOP-2: Access Modifier Demo
 *
 * TEACHING FLOW:
 *   1. Show Elephant with 4 different access levels on its fields.
 *   2. Show BabyElephant (in a DIFFERENT package) — what it can/can't access.
 *   3. Prove that sub-packages are SEPARATE packages (flat hierarchy).
 *
 * ACCESS MODIFIER TABLE:
 *   ┌────────────┬───────┬─────────┬────────────┬──────────┐
 *   │  Modifier  │ Class │ Package │ Subclass   │ World    │
 *   ├────────────┼───────┼─────────┼────────────┼──────────┤
 *   │ private    │  ✓    │  ✗      │  ✗         │  ✗       │
 *   │ default    │  ✓    │  ✓      │  ✗         │  ✗       │
 *   │ protected  │  ✓    │  ✓      │  ✓         │  ✗       │
 *   │ public     │  ✓    │  ✓      │  ✓         │  ✓       │
 *   └────────────┴───────┴─────────┴────────────┴──────────┘
 *
 *   "Subclass" column applies even if the subclass is in a DIFFERENT package.
 *   "default" does NOT grant access to subclasses in other packages.
 *
 * Run: Right-click → Run 'AccessModifierDemo.main()'
 */
public class AccessModifierDemo {

    public static void main(String[] args) {

        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.println("║  Access Modifier Demo                           ║");
        System.out.println("╚══════════════════════════════════════════════════╝");


        // ── Elephant: all access levels ──
        System.out.println("\n── Elephant (all fields) ──");
        Elephant e = new Elephant();
        System.out.println("  " + e);

        // From HERE (Class_3 package — different from animals package):
        System.out.println("\n── Accessing from Class_3 package (not a subclass) ──");
        System.out.println("  name:   " + e.name + "  (public — always accessible)");
        // System.out.println(e.legs);     // COMPILE ERROR if uncommented: protected
        // System.out.println(e.height);   // COMPILE ERROR if uncommented: default
        // System.out.println(e.id);       // COMPILE ERROR if uncommented: private
        System.out.println("  legs:   [protected — not accessible, we're not a subclass]");
        System.out.println("  height: [default — not accessible, different package]");
        System.out.println("  id:     " + e.getId() + " (private — only via getter)");


        // ── BabyElephant: subclass in a DIFFERENT package ──
        System.out.println("\n── BabyElephant (subclass in different package) ──");
        BabyElephant baby = new BabyElephant();
        baby.describe();


        // ── Summary ──
        System.out.println("\n── Key Takeaway ──");
        System.out.println("  Sub-packages are SEPARATE packages in Java.");
        System.out.println("  animals and animals.baby have no special relationship.");
        System.out.println("  default (package-private) does NOT cross package boundaries.");
        System.out.println("  protected DOES cross — but only for subclasses.");
    }
}
