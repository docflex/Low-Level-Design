package com.scaler.LLD.Module_2.Class_7.Decorator;

public class Decaf implements Coffee {

    @Override
    public int cost() {
        return 120;
    }

    @Override
    public String description() {
        return "Decaf";
    }
}
