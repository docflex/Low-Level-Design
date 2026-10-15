package com.scaler.LLD.Module_1.Part_2_Concurrency.Class_2;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Concurrency-2: BenchmarkingDemo — Raw Threads vs Thread Pool
 *
 * Compares two approaches to execute 100,000 tasks:
 *
 *   Approach 1: Create a NEW Thread per task (raw threads)
 *     → Huge overhead: each new Thread() allocates ~512KB-1MB stack,
 *       plus OS-level thread creation and scheduling setup.
 *
 *   Approach 2: Fixed Thread Pool (reuses a fixed set of threads)
 *     → Threads are created once, tasks are queued and picked up
 *       by idle threads. No repeated creation/destruction overhead.
 *
 * Typical result: Pool is 5-30x faster than raw threads.
 *
 * Thread Pool Sizing (for CPU-bound work):
 *   Runtime.getRuntime().availableProcessors()
 *
 *   For IO-bound work (network, DB): cores * (1 + waitTime/computeTime)
 *   because threads spend time waiting, not using CPU.
 *
 * NOTE: Using 100K tasks (not 1M) so the raw-threads approach
 *   finishes in reasonable time for a demo.
 */
public class BenchmarkingDemo {

    private static final int TASK_COUNT = 100_000;

    public static void main(String[] args) throws InterruptedException {

        // ── Approach 1: Raw Threads (new Thread per task) ──
        System.out.println("=== Approach 1: Raw Threads (" + TASK_COUNT + " threads) ===");
        long start = System.currentTimeMillis();

        Thread[] threads = new Thread[TASK_COUNT];
        for (int i = 1; i <= TASK_COUNT; i++) {
            IndividualNumberPrinter printer = new IndividualNumberPrinter(i);
            threads[i - 1] = new Thread(printer);
            threads[i - 1].start();
        }
        // Wait for all threads to finish
        for (Thread t : threads) {
            t.join();
        }

        long rawTime = System.currentTimeMillis() - start;
        System.out.println("Raw threads time: " + rawTime + "ms\n");


        // ── Approach 2: Fixed Thread Pool ──
        int cores = Runtime.getRuntime().availableProcessors();
        System.out.println("=== Approach 2: FixedThreadPool(" + cores + ") ===");
        start = System.currentTimeMillis();

        ExecutorService executorService = Executors.newFixedThreadPool(cores);

        for (int i = 1; i <= TASK_COUNT; i++) {
            IndividualNumberPrinter printer = new IndividualNumberPrinter(i);
            executorService.execute(printer);
        }

        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.MINUTES);

        long poolTime = System.currentTimeMillis() - start;
        System.out.println("Thread pool time: " + poolTime + "ms\n");


        // ── Summary ──
        System.out.println("=== Summary ===");
        System.out.println("Raw threads: " + rawTime + "ms");
        System.out.println("Thread pool: " + poolTime + "ms");
        System.out.println("Speedup:     ~" + String.format("%.1f", (double) rawTime / poolTime) + "x");
    }
}
