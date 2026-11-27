package com.scaler.LLD.Module_2.Class_8.aicorner;

import com.scaler.LLD.Module_2.Class_8.Strategy.Path;
import com.scaler.LLD.Module_2.Class_8.Strategy.PathCalculator;

import java.time.Clock;
import java.time.LocalTime;

/**
 * Fix: the rush-hour rule lives with the strategy it belongs to, as a decorator around it
 * (Module 2, Class 7). GoogleMaps no longer knows anything about cars or traffic.
 * The Clock is injected so the rule can be tested at any time of day.
 */
public class RushHourAwarePathCalculator implements PathCalculator {
    private final PathCalculator inner;
    private final Clock clock;
    private final int delayMinutes;

    public RushHourAwarePathCalculator(PathCalculator inner, Clock clock, int delayMinutes) {
        this.inner = inner;
        this.clock = clock;
        this.delayMinutes = delayMinutes;
    }

    @Override
    public Path calculatePath(String source, String destination) {
        Path path = inner.calculatePath(source, destination);
        if (!isRushHour()) {
            return path;
        }
        // Return a new Path instead of changing the one the inner strategy gave us
        return new Path(path.getDistance(), path.getEtaInHours() + delayMinutes / 60.0);
    }

    private boolean isRushHour() {
        LocalTime now = LocalTime.now(clock);
        return !now.isBefore(LocalTime.of(8, 0)) && now.isBefore(LocalTime.of(10, 0));
    }
}
