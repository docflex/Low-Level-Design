package com.scaler.LLD.Module_2.Class_8.Strategy;

public class CarPathCalculator implements PathCalculator {
    @Override
    public Path calculatePath(String source, String destination) {
        System.out.println("Travelling via Car");
        return new Path(11, 0.45);
    }
}
