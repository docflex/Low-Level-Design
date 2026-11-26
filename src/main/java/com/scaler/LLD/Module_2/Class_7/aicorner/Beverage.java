package com.scaler.LLD.Module_2.Class_7.aicorner;

/**
 * AI Corner: Beverage with a way to ask "does this drink contain an add-on of type X?".
 * A base beverage only contains itself; each add-on also checks the drink it wraps.
 */
public interface Beverage {
    int cost();

    String description();

    default boolean contains(Class<? extends Beverage> type) {
        return type.isInstance(this);
    }
}
