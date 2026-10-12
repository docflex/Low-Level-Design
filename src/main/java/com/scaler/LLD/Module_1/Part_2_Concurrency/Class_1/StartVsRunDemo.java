package com.scaler.LLD.Module_1.Part_2_Concurrency.Class_1;

/**
 * Concurrency-1: start() vs run() — Critical Distinction
 *
 * t.start():
 *   - JVM creates a NEW OS thread, sets up its stack,
 *     registers it with the thread scheduler, THEN calls run().
 *   - run() executes on the NEW thread.
 *
 * t.run():
 *   - Just a normal method call on the CURRENT thread.
 *   - No new thread is created. Completely sequential.
 *
 * Run this demo and observe:
 *   - With start(): thread names differ (main vs Thread-0)
 *   - With run(): everything runs on "main"
 */
public class StartVsRunDemo {

    public static void main(String[] args) {

        Runnable task = () -> {
            String name = Thread.currentThread().getName();
            for (int i = 1; i <= 5; i++) {
                System.out.println("  [" + name + "] working... " + i);
            }
        };

        Thread t = new Thread(task);

        // ── Using start() — creates a new thread ──
        System.out.println("=== t.start() ===");
        System.out.println("[" + Thread.currentThread().getName() + "] Before start");
        t.start();
        System.out.println("[" + Thread.currentThread().getName() + "] After start (may print before worker finishes!)");

        // Wait for the started thread to finish before the next demo
        try { t.join(); } catch (InterruptedException e) { /* ignored */ }

        System.out.println();

        // ── Using run() — NO new thread, just a method call ──
        System.out.println("=== t.run() (direct call — NO new thread) ===");
        Thread t2 = new Thread(task);
        System.out.println("[" + Thread.currentThread().getName() + "] Before run");
        t2.run();   // Runs on main thread — completely sequential
        System.out.println("[" + Thread.currentThread().getName() + "] After run (always prints after worker finishes)");
    }
}
