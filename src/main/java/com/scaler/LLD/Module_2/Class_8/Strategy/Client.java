package com.scaler.LLD.Module_2.Class_8.Strategy;

/** Strategy + Registry: the same GoogleMaps code with a different path algorithm per mode. */
public class Client {
    public static void main(String[] args) {
        System.out.println("=== 1. Strategy injected directly ===");
        GoogleMaps direct = new GoogleMaps(new CarPathCalculator());
        print(direct.calculatePath("Delhi", "Mumbai"));

        System.out.println("\n=== 2. Strategy chosen from the mode name (e.g. what the user tapped) ===");
        PathCalculatorRegistry.addPath("Bike", new BikePathCalculator());   // new mode, no class edited
        System.out.println("registered modes: " + PathCalculatorRegistry.modes());
        for (String mode : new String[]{"Car", "Walk", "Bike", "car"}) {
            System.out.print("[" + mode + "] ");
            print(new GoogleMaps(mode).calculatePath("Delhi", "Mumbai"));
        }

        System.out.println("\n=== 3. One shared strategy object per mode ===");
        System.out.println("same Car calculator for two lookups? "
                + (PathCalculatorRegistry.getPath("Car") == PathCalculatorRegistry.getPath("car")));

        System.out.println("\n=== 4. Unknown mode fails immediately ===");
        try {
            new GoogleMaps("Metro");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    static void print(Path path) {
        System.out.println("  Path: " + path.getDistance() + " km, Time " + path.getEtaInHours() + " h");
    }
}
