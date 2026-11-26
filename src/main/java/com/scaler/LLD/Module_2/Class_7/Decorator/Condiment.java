package com.scaler.LLD.Module_2.Class_7.Decorator;

/**
 * Decorator base class. A condiment IS-A Coffee (same type) and HAS-A Coffee (the one it wraps),
 * so a decorated coffee can itself be wrapped again: new Mocha(new WhippedCream(new Espresso())).
 */
public abstract class Condiment implements Coffee {
    protected final Coffee coffee; // Don't worry about the protected for now

    public Condiment(Coffee coffee) {
        this.coffee = coffee;
    }

    @Override
    public abstract int cost();

    @Override
    public abstract String description();
}
