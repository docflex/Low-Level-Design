package com.scaler.LLD.Module_2.Class_8.Strategy;

import java.util.Scanner;

/**
 * Strategy, step by step:
 *  1. One method with an if-else per mode -- and the "==" string bug
 *  2. Factory that returned null for an unknown mode
 *  3. Factory that creates a new object every call vs a registry that shares one
 */
public class StrategyEvolutionDemo {

    /** Step 1: every mode's algorithm inside one class, chosen with ==. */
    static class NaiveGoogleMaps {
        String calculatePath(String mode) {
            if (mode == "Car") {
                return "car route";
            } else if (mode == "Walk") {
                return "walking route";
            }
            return "no route";
        }
    }

    /** Step 2: the factory as first written -- case-sensitive and returns null. */
    static PathCalculator nullReturningFactory(String mode) {
        if (mode.equals("Car")) {
            return new CarPathCalculator();
        } else if (mode.equals("Walk")) {
            return new WalkingPathCalculator();
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println("=== Step 1: if-else with == on strings ===");
        NaiveGoogleMaps naive = new NaiveGoogleMaps();
        String typedByUser = new Scanner("Car\n").nextLine();      // same text, built at runtime
        System.out.println("  literal \"Car\" -> " + naive.calculatePath("Car"));
        System.out.println("  user typed \"Car\" -> " + naive.calculatePath(typedByUser)
                + "  (== compares object identity; use equals())");

        System.out.println("\n=== Step 2: factory that returns null ===");
        PathCalculator calculator = nullReturningFactory("car");   // lower-case input
        System.out.println("  factory(\"car\") -> " + calculator);
        try {
            calculator.calculatePath("Delhi", "Mumbai");
        } catch (NullPointerException e) {
            System.out.println("  crash happens later, at calculatePath(): NullPointerException");
        }
        try {
            PathCalculatorFactory.getPathCalculator("Metro");
        } catch (IllegalArgumentException e) {
            System.out.println("  fixed factory fails at the lookup: " + e.getMessage());
        }

        System.out.println("\n=== Step 3: new object per call vs shared strategy ===");
        PathCalculator a = PathCalculatorFactory.getPathCalculator("Car");
        PathCalculator b = PathCalculatorFactory.getPathCalculator("Car");
        System.out.println("  factory, two calls -> same object? " + (a == b) + "  (a new CarPathCalculator each time)");
        PathCalculator c = PathCalculatorRegistry.getPath("Car");
        PathCalculator d = PathCalculatorRegistry.getPath("Car");
        System.out.println("  registry, two calls -> same object? " + (c == d)
                + "  (safe to share: the calculator has no state)");
    }
}
