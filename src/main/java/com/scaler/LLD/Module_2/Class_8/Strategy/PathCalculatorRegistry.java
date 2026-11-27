package com.scaler.LLD.Module_2.Class_8.Strategy;

import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Registry of strategies: one shared, stateless calculator per mode, created once at class load.
 * New modes are added with addPath(...) -- no if-else to edit. Names are case-insensitive;
 * an unknown mode fails immediately instead of returning null.
 */
public class PathCalculatorRegistry {
    private static final Map<String, PathCalculator> pathRegistry = new ConcurrentHashMap<>();

    static {
        addPath("Car", new CarPathCalculator());
        addPath("Walk", new WalkingPathCalculator());
    }

    private PathCalculatorRegistry() {
    }

    public static void addPath(String name, PathCalculator pathCalculator) {
        pathRegistry.put(key(name), pathCalculator);
    }

    public static PathCalculator getPath(String name) {
        PathCalculator calculator = pathRegistry.get(key(name));
        if (calculator == null) {
            throw new IllegalArgumentException("Unknown mode: " + name + ". Registered: " + modes());
        }
        return calculator;
    }

    public static Set<String> modes() {
        return new TreeSet<>(pathRegistry.keySet());
    }

    private static String key(String name) {
        return name.toLowerCase(Locale.ROOT);
    }
}
