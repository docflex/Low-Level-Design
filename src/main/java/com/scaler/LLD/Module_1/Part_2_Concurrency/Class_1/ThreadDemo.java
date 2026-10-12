package com.scaler.LLD.Module_1.Part_2_Concurrency.Class_1;

/**
 * Concurrency-1: ThreadDemo — Main demo for threads.
 *
 * Demonstrates:
 *   1. Single Runnable (NumberPrinter) on a separate thread.
 *   2. 100 individual Runnables, each on its own thread.
 *   3. Non-deterministic output order.
 *   4. "Hello Again" prints BEFORE workers finish because
 *      the main thread continues executing after t.start().
 *
 * IMPORTANT MISCONCEPTION CORRECTED:
 *   The script says "Main Thread remains alive till all Tasks it spun up
 *   haven't completed." This is WRONG. The main thread finishes when
 *   main() returns. What keeps the JVM alive is that spawned threads
 *   are non-daemon by default. If you want main to WAIT, use t.join().
 *
 * NOTE: Creating 100 threads is fine for a demo. In production, use
 *   a Thread Pool (ExecutorService) — each thread allocates ~512KB-1MB
 *   of stack memory, so creating thousands is wasteful.
 */
public class ThreadDemo {
    public static void main(String[] args) {
        System.out.println("[" + Thread.currentThread().getName() + "] Hello");

        // --- Approach 1: Single thread prints all 100 numbers ---
        // Uncomment to try:
        // NumberPrinter numberPrinter = new NumberPrinter();
        // Thread t = new Thread(numberPrinter);
        // t.start();

        // --- Approach 2: 100 threads, each prints one number ---
        for (int i = 1; i <= 100; i++) {
            IndividualNumberPrinter printer = new IndividualNumberPrinter(i);
            Thread t = new Thread(printer);
            t.start();
        }

        // This prints BEFORE workers finish — main thread doesn't wait!
        System.out.println("[" + Thread.currentThread().getName() + "] Hello Again");
    }
}
