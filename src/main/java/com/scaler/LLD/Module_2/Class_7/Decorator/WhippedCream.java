package com.scaler.LLD.Module_2.Class_7.Decorator;

public class WhippedCream extends Condiment {
    public WhippedCream(Coffee coffee) {
        super(coffee);
    }

    @Override
    public int cost() {
        return coffee.cost() + 60; // Suppose 60 for the Whip
    }

    @Override
    public String description() {
        return coffee.description() + " with Whipped Cream";
    }
}
