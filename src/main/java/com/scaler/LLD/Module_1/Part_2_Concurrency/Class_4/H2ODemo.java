package com.scaler.LLD.Module_1.Part_2_Concurrency.Class_4;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Concurrency-4: H2ODemo — runnable demo for Building H2O.
 *
 * Creates 6 hydrogen threads and 3 oxygen threads (enough for 3 water molecules).
 * Output should always be groups of HHO (order within a group may vary: HHO, HOH, OHH).
 */
public class H2ODemo {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== Building H2O Demo ===");
        System.out.println("6 H threads + 3 O threads = 3 water molecules\n");

        H2O h2o = new H2O();
        ExecutorService pool = Executors.newCachedThreadPool();
        StringBuilder output = new StringBuilder();

        Runnable printH = () -> {
            synchronized (output) { output.append("H"); }
        };
        Runnable printO = () -> {
            synchronized (output) { output.append("O"); }
        };

        // 6 hydrogen threads
        for (int i = 0; i < 6; i++) {
            pool.execute(() -> {
                try { h2o.hydrogen(printH); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            });
        }

        // 3 oxygen threads
        for (int i = 0; i < 3; i++) {
            pool.execute(() -> {
                try { h2o.oxygen(printO); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            });
        }

        pool.shutdown();
        pool.awaitTermination(5, TimeUnit.SECONDS);

        String result = output.toString();
        System.out.println("Output: " + result);

        // Validate: every 3-char group has exactly 2 H and 1 O
        boolean valid = true;
        for (int i = 0; i < result.length(); i += 3) {
            String group = result.substring(i, Math.min(i + 3, result.length()));
            long hCount = group.chars().filter(c -> c == 'H').count();
            long oCount = group.chars().filter(c -> c == 'O').count();
            if (hCount != 2 || oCount != 1) {
                valid = false;
                break;
            }
        }
        System.out.println("Valid H2O groups: " + valid);
    }
}
