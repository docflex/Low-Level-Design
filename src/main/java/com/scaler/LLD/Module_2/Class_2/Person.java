package com.scaler.LLD.Module_2.Class_2;

public class Person {
    static {
        System.out.println("Person Class Has Been Loaded");
    }

    private final String name;

    public Person(String name) {
        this.name = name;
        System.out.println("Constructor (" + name + ")");
    }

    public void sayHello() {
        System.out.println("Hello from " + name);
    }
}
