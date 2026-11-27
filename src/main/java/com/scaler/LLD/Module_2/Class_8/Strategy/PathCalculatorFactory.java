package com.scaler.LLD.Module_2.Class_8.Strategy;

/**
 * Simple Factory from class: picks a strategy from the mode name.
 * Creates a NEW calculator on every call and needs editing for every new mode --
 * PathCalculatorRegistry fixes both. Kept to show that step (see StrategyEvolutionDemo).
 */
public class PathCalculatorFactory {
    public static PathCalculator getPathCalculator(String mode) {
        if (mode.equalsIgnoreCase("Car")) {
            return new CarPathCalculator();
        } else if (mode.equalsIgnoreCase("Walk")) {
            return new WalkingPathCalculator();
        }
        throw new IllegalArgumentException("Unknown mode: " + mode);
    }
}
