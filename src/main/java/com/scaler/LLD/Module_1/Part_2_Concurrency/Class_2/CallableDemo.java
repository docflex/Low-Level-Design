package com.scaler.LLD.Module_1.Part_2_Concurrency.Class_2;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.Future;
import java.util.concurrent.ExecutionException;

/**
 * Concurrency-2: CallableDemo — Callable + Future
 *
 * Key concepts:
 *   - execute(Runnable)  → fire-and-forget, no return value
 *   - submit(Callable)   → returns a Future<T>
 *   - Future.get()       → BLOCKS until result is ready
 *   - Submitting is non-blocking; getting the result is blocking
 *
 * FIX APPLIED: Original had output.get(5, TimeUnit.SECONDS) but
 *   NumberMultiplier slept for 10s → guaranteed TimeoutException.
 *   Reduced sleep to 2s and use standard get() so the demo works.
 *
 * Two tasks submitted to a 2-thread pool, each takes ~2s.
 * Both run in parallel → total time ≈ 2s (not 4s).
 */
public class CallableDemo {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        NumberMultiplier task1 = new NumberMultiplier(100);   // returns 100*5 = 500
        NumberMultiplier task2 = new NumberMultiplier(200);   // returns 200*5 = 1000

        ExecutorService executorService = Executors.newFixedThreadPool(2);

        long start = System.currentTimeMillis();

        // submit() is NON-BLOCKING — returns immediately with a Future
        Future<Integer> future1 = executorService.submit(task1);
        Future<Integer> future2 = executorService.submit(task2);

        // This prints immediately — main thread isn't waiting
        System.out.println("Hello (printed immediately — tasks running in background)");

        // get() is BLOCKING — main waits here until the result is ready
        System.out.println("Result 1: " + future1.get());   // blocks until task1 completes
        System.out.println("Result 2: " + future2.get());   // task2 likely already done by now

        long elapsed = System.currentTimeMillis() - start;
        System.out.println("Total time: " + elapsed + "ms (both tasks ran in parallel, not sequentially)");

        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.MINUTES);
    }
}
