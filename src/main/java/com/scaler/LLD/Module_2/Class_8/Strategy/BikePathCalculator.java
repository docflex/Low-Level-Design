package com.scaler.LLD.Module_2.Class_8.Strategy;

/** A strategy added after the others -- registered from the client, no existing class edited. */
public class BikePathCalculator implements PathCalculator {
    @Override
    public Path calculatePath(String source, String destination) {
        System.out.println("Travelling via Bike");
        return new Path(9, 0.6);
    }
}
