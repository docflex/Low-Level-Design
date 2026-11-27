package com.scaler.LLD.Module_2.Class_8.Strategy;

public class WalkingPathCalculator implements PathCalculator {
    @Override
    public Path calculatePath(String source, String destination) {
        System.out.println("Travelling via Walking");
        // Hard Coded for now but in real life you would use some sort of Algo
        return new Path(5, 2.5);
    }
}
