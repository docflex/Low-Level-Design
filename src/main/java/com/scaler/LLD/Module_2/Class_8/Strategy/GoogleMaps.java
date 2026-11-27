package com.scaler.LLD.Module_2.Class_8.Strategy;

import java.util.Objects;

/**
 * Context: GoogleMaps doesn't know HOW a path is calculated -- it delegates to whichever
 * PathCalculator strategy it was given.
 */
public class GoogleMaps {

    private final PathCalculator pathCalculator;

    /** Strategy injected directly. */
    public GoogleMaps(PathCalculator pathCalculator) {
        this.pathCalculator = Objects.requireNonNull(pathCalculator, "pathCalculator");
    }

    /** Strategy chosen from a mode name (e.g. user input) through the registry. */
    public GoogleMaps(String mode) {
        this(PathCalculatorRegistry.getPath(mode));
    }

    public Path calculatePath(String source, String destination) {
        return pathCalculator.calculatePath(source, destination);
    }
}
