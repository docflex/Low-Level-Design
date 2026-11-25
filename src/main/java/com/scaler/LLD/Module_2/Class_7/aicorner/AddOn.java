package com.scaler.LLD.Module_2.Class_7.aicorner;

/** Decorator base: walks down the chain when asked what the drink contains. */
public abstract class AddOn implements Beverage {
    protected final Beverage beverage;

    protected AddOn(Beverage beverage) {
        this.beverage = beverage;
    }

    @Override
    public boolean contains(Class<? extends Beverage> type) {
        return type.isInstance(this) || beverage.contains(type);
    }
}
