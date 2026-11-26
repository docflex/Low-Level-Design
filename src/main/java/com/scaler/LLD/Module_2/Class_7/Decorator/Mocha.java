package com.scaler.LLD.Module_2.Class_7.Decorator;

public class Mocha extends Condiment {
    public Mocha(Coffee coffee) {
        super(coffee);
    }

    @Override
    public int cost() {
        return coffee.cost() + 50; // Rs. 50 for Mocha as a Topping.
    }

    @Override
    public String description() {
        return coffee.description() + " with Mocha";
    }
}
