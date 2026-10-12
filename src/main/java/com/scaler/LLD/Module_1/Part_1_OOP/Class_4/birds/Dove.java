package com.scaler.LLD.Module_1.Part_1_OOP.Class_4.birds;

/** Dove CAN fly but CANNOT dance. */
public class Dove extends Bird implements Flyable {

    public Dove() { super("Dove"); }

    @Override
    public void fly() {
        System.out.println("Dove is flying gracefully");
    }
}
