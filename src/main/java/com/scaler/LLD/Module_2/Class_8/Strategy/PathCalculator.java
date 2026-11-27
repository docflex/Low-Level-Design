package com.scaler.LLD.Module_2.Class_8.Strategy;

/** Strategy: every way of getting from A to B (car, walk, bike, ...) implements this. */
public interface PathCalculator {
    Path calculatePath(String source, String destination);
}
