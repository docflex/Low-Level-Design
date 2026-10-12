package com.scaler.LLD.Module_1.Part_2_Concurrency.Class_1;

/**
 * Concurrency-1: Sequential vs Threaded — Performance Comparison
 *
 * Simulates 3 independent tasks (each takes ~500ms).
 *
 * Sequential: ~1500ms  (500 + 500 + 500)
 * Threaded:   ~500ms   (all 3 run in parallel → bottleneck = slowest task)
 *
 * This is the core "WHY" of multithreading:
 *   - Independent tasks can overlap.
 *   - Total time = max(task times), not sum(task times).
 *
 * IMPORTANT: The main thread does NOT automatically wait for spawned threads.
 * We use t.join() to block main until the thread finishes. Without join(),
 * main would print "Done" before tasks complete.
 */
public class SequentialVsThreadedDemo {

    static void simulateWork(String taskName, long millis) {
        System.out.println("  [" + Thread.currentThread().getName() + "] "
                + taskName + " started");
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("  [" + Thread.currentThread().getName() + "] "
                + taskName + " done (" + millis + "ms)");
    }

    public static void main(String[] args) throws InterruptedException {

        // ── Sequential ──
        System.out.println("=== Sequential Execution ===");
        long start = System.currentTimeMillis();

        simulateWork("Task A", 500);
        simulateWork("Task B", 500);
        simulateWork("Task C", 500);

        long seqTime = System.currentTimeMillis() - start;
        System.out.println("Sequential total: " + seqTime + "ms\n");


        // ── Threaded ──
        System.out.println("=== Threaded Execution ===");
        start = System.currentTimeMillis();

        Thread t1 = new Thread(() -> simulateWork("Task A", 500));
        Thread t2 = new Thread(() -> simulateWork("Task B", 500));
        Thread t3 = new Thread(() -> simulateWork("Task C", 500));

        t1.start();
        t2.start();
        t3.start();

        // join() makes main WAIT for each thread to finish
        t1.join();
        t2.join();
        t3.join();

        long parTime = System.currentTimeMillis() - start;
        System.out.println("Threaded total:   " + parTime + "ms\n");


        // ── Summary ──
        System.out.println("=== Summary ===");
        System.out.println("Sequential: " + seqTime + "ms");
        System.out.println("Threaded:   " + parTime + "ms");
        System.out.println("Speedup:    ~" + String.format("%.1f", (double) seqTime / parTime) + "x");
    }
}
