package com.scaler.LLD.Module_2.Class_8.aicorner;

import com.scaler.LLD.Module_2.Class_8.Strategy.Path;
import com.scaler.LLD.Module_2.Class_8.Strategy.PathCalculator;
import com.scaler.LLD.Module_2.Class_8.Strategy.PathCalculatorFactory;

import java.time.Clock;
import java.time.LocalTime;

/**
 * The code given to the AI for review (adapted to this repo's class names). Problems:
 *  1. The context checks the mode with a string -- car-specific logic outside the car strategy
 *  2. "car" vs "Car": the factory accepts any case, this check doesn't, so the delay is silently skipped
 *  3. It mutates the Path returned by the strategy
 *  4. Every new rule (bike lanes, tolls, ...) means another if here -- the if-else Strategy removed
 */
public class BuggyGoogleMaps {
    private final Clock clock;

    public BuggyGoogleMaps(Clock clock) {
        this.clock = clock;
    }

    public Path findPath(String source, String destination, String mode) {
        PathCalculator pc = PathCalculatorFactory.getPathCalculator(mode);
        Path path = pc.calculatePath(source, destination);
        if (mode.equals("car") && isRushHour()) {
            path.setEtaInHours(path.getEtaInHours() + 20 / 60.0);   // add 20 minutes
        }
        return path;
    }

    private boolean isRushHour() {
        LocalTime now = LocalTime.now(clock);
        return !now.isBefore(LocalTime.of(8, 0)) && now.isBefore(LocalTime.of(10, 0));
    }
}
