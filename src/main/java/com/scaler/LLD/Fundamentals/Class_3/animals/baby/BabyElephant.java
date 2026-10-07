package com.scaler.LLD.Fundamentals.Class_3.animals.baby;

import com.scaler.LLD.Fundamentals.Class_3.animals.Elephant;

/**
 * LLD 3 — OOP-2: BabyElephant — Access Modifier Demo (Different Package)
 *
 * BabyElephant is in package `animals.baby` — a DIFFERENT package
 * from Elephant which is in `animals`.
 *
 * KEY INSIGHT: Sub-packages are treated as SEPARATE packages in Java.
 *   `animals` and `animals.baby` have NO special access relationship.
 *
 * What BabyElephant can access from Elephant:
 *   ✓ name   (public)      → accessible from everywhere
 *   ✓ legs   (protected)   → accessible because BabyElephant is a subclass
 *   ✗ height (default)     → NOT accessible — different package!
 *   ✗ id     (private)     → NOT accessible — only Elephant itself can
 *
 * Access Modifier Hierarchy (least → most accessible):
 *   private → default → protected → public
 */
public class BabyElephant extends Elephant {

    public void describe() {
        // ✓ public — accessible everywhere
        System.out.println("  Name:   " + name + " (public — accessible)");

        // ✓ protected — accessible because we're a subclass of Elephant
        System.out.println("  Legs:   " + legs + " (protected — accessible as subclass)");

        // ✗ default — NOT accessible from a different package!
        // System.out.println("  Height: " + height);
        //     COMPILE ERROR: height has default access in Elephant
        //     and BabyElephant is in a DIFFERENT package (animals.baby ≠ animals)
        System.out.println("  Height: [NOT ACCESSIBLE — default, different package]");

        // ✗ private — NOT accessible outside Elephant class
        // System.out.println("  ID:     " + id);
        //     COMPILE ERROR: id has private access in Elephant
        System.out.println("  ID:     " + getId() + " (private — accessed via public getter)");
    }
}
