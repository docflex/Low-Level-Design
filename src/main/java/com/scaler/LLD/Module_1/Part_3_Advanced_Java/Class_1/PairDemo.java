package com.scaler.LLD.Module_1.Part_3_Advanced_Java.Class_1;

import java.util.ArrayList;
import java.util.List;

/**
 * Java Advanced Concepts - 1 [Generics]
 *
 * Demonstrates the problem with Object-based Pairs (no type safety)
 * vs generic Pair<T,V> (compile-time type safety, no manual casting).
 */
public class PairDemo {
    public static void main(String[] args) {

        // ── Part 1: ObjectPair -- no type safety ──
        System.out.println("=== ObjectPair (no type safety) ===");

        List<ObjectPair> coordinates = new ArrayList<>();

        ObjectPair p1 = new ObjectPair(12, 13);
        ObjectPair p2 = new ObjectPair(13, 2);
        ObjectPair p3 = new ObjectPair(2, 43);

        coordinates.add(p1);
        coordinates.add(p2);
        coordinates.add(p3);

        System.out.println("Coordinates: " + coordinates);

        // Problem: nothing stops us from inserting a completely wrong type
        ObjectPair p4 = new ObjectPair("Vault", p1);
        coordinates.add(p4);
        System.out.println("After adding wrong type: " + coordinates);

        // Safe cast works for p1...
        int coord1 = (int) p1.getX();
        System.out.println("p1.x cast to int: " + coord1);

        // ...but p4.getX() is a String -- ClassCastException at runtime!
        try {
            int coord2 = (int) p4.getX();
            System.out.println("p4.x cast to int: " + coord2);
        } catch (ClassCastException e) {
            System.out.println("ClassCastException: " + e.getMessage());
        }

        // ── Part 2: Generic Pair<T,V> -- type safety ──
        System.out.println("\n=== Generic Pair<T,V> (type safe) ===");

        Pair<Integer, Integer> intPair = new Pair<>(1, 2);
        System.out.println("Integer pair: " + intPair);

        Pair<String, Double> namePspPair = new Pair<>("Rehber", 87.5);
        System.out.println("Name-PSP pair: " + namePspPair.getX() + " " + namePspPair.getY());

        // This would NOT compile (caught at compile time, not runtime):
        // Pair<Integer, Integer> bad = new Pair<>("oops", 2);

        // ── Part 3: Static generic method ──
        System.out.println("\n=== Static generic method ===");
        Pair.doSomething(1.5);
        Pair.doSomething("Hello");
        Pair.doSomething(42);
    }
}
