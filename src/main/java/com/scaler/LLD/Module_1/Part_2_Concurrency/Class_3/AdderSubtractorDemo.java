package com.scaler.LLD.Module_1.Part_2_Concurrency.Class_3;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Concurrency-3: AdderSubtractorDemo — race condition & synchronization.
 *
 * Adder adds 1+2+...+100 = 5050 to the Counter.
 * Subtractor subtracts 1+2+...+100 = 5050 from the Counter.
 * Expected result: 0.
 *
 * With synchronized methods on Counter → always 0.
 * Without synchronization → non-deterministic (race condition).
 *
 * Runs 5 times to show the result is consistently 0.
 *
 * FIX APPLIED: Changed shutdownNow() → shutdown(). shutdownNow()
 *   interrupts running tasks; shutdown() is the graceful approach
 *   (both tasks are already done at that point anyway).
 */
public class AdderSubtractorDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        System.out.println("=== Adder-Subtractor Demo (synchronized Counter) ===");
        System.out.println("Expected: 0 every time (Adder +5050, Subtractor -5050)\n");

        for (int run = 1; run <= 5; run++) {
            Counter counter = new Counter(0);
            AdderTask adderTask = new AdderTask(counter);
            SubtractorTask subtractorTask = new SubtractorTask(counter);

            ExecutorService executorService = Executors.newFixedThreadPool(2);
            Future<Void> adderFuture = executorService.submit(adderTask);
            Future<Void> subtractorFuture = executorService.submit(subtractorTask);

            adderFuture.get();       // block until adder completes
            subtractorFuture.get();  // block until subtractor completes

            System.out.println("Run " + run + ": counter = " + counter.getX());

            executorService.shutdown();
        }
    }
}
