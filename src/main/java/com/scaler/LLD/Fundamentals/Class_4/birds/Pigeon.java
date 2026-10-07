package com.scaler.LLD.Fundamentals.Class_4.birds;

/** Pigeon CAN fly AND CAN dance. Implements both interfaces. */
public class Pigeon extends Bird implements Flyable, Danceable {

    public Pigeon() { super("Pigeon"); }

    @Override
    public void fly() {
        System.out.println("Pigeon is flying");
    }

    @Override
    public void dance() {
        System.out.println("Pigeon is dancing");
    }
}
