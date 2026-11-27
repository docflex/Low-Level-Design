package com.scaler.LLD.Module_2.Class_8.aicorner;

import com.scaler.LLD.Module_2.Class_8.Strategy.CarPathCalculator;
import com.scaler.LLD.Module_2.Class_8.Strategy.GoogleMaps;
import com.scaler.LLD.Module_2.Class_8.Strategy.Path;
import com.scaler.LLD.Module_2.Class_8.Strategy.WalkingPathCalculator;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;

/**
 * AI Corner: review the rush-hour code, then fix it.
 * Fixed clocks make the result the same on every run (9:00 = rush hour, 14:00 = not).
 */
public class RushHourReviewDemo {

    static Clock at(int hour) {
        return Clock.fixed(Instant.parse(String.format("2026-11-27T%02d:00:00Z", hour)), ZoneOffset.UTC);
    }

    static String eta(Path path) {
        return String.format("%.2f h", path.getEtaInHours());
    }

    public static void main(String[] args) {
        System.out.println("=== Code under review, at 9:00 (rush hour) ===");
        BuggyGoogleMaps buggy = new BuggyGoogleMaps(at(9));
        System.out.println("  mode \"car\" -> " + eta(buggy.findPath("Delhi", "Gurgaon", "car")) + "  (delay added)");
        System.out.println("  mode \"Car\" -> " + eta(buggy.findPath("Delhi", "Gurgaon", "Car"))
                + "  (same mode, no delay: the factory ignores case, the rush-hour check doesn't)");

        System.out.println("\n=== Fixed: the rule decorates the car strategy; GoogleMaps has no if ===");
        for (int hour : new int[]{9, 14}) {
            GoogleMaps carMaps = new GoogleMaps(new RushHourAwarePathCalculator(new CarPathCalculator(), at(hour), 20));
            GoogleMaps walkMaps = new GoogleMaps(new WalkingPathCalculator());
            System.out.println("  at " + hour + ":00");
            System.out.println("    car  -> " + eta(carMaps.calculatePath("Delhi", "Gurgaon")));
            System.out.println("    walk -> " + eta(walkMaps.calculatePath("Delhi", "Gurgaon")) + "  (unaffected)");
        }
    }
}
