package com.scaler.LLD.Module_1.Part_3_Advanced_Java.Class_1;

public class Animal extends Creature {
    String name;

    public Animal(String name) {
        this.name = name;
    }

    public Animal() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
