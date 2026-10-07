package com.scaler.LLD.Fundamentals.Class_4.birds;

/** Penguin CANNOT fly but CAN dance. Only implements Danceable. */
public class Penguin extends Bird implements Danceable {

    public Penguin() { super("Penguin"); }

    @Override
    public void dance() {
        System.out.println("Penguin is dancing happily");
    }

    // No fly() — Penguin doesn't implement Flyable.
    // It can never be passed to flyAllBirds(List<Flyable>).
    // The TYPE SYSTEM prevents the bug at COMPILE TIME.
}
