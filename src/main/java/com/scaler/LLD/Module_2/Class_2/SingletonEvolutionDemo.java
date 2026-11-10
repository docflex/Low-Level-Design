package com.scaler.LLD.Module_2.Class_2;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Every version of DBConnectionPool from the class, runnable side by side.
 * The constructor sleeps 50 ms to stand in for a heavy pool setup -- that widens the race window
 * so the thread-safety bugs show up on every run instead of occasionally.
 */
public class SingletonEvolutionDemo {
    static final AtomicInteger created = new AtomicInteger();

    static void heavySetup() {
        created.incrementAndGet();
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /** Step 1: public constructor -- anyone can create as many as they like. */
    static class PublicConstructorPool {
        PublicConstructorPool() { heavySetup(); }
    }

    /** Step 2: private constructor + static getInstance() that still calls new every time. */
    static class AlwaysNewPool {
        private AlwaysNewPool() { heavySetup(); }
        static AlwaysNewPool getInstance() { return new AlwaysNewPool(); }
    }

    /** Step 3: lazy null check -- correct for one thread, broken for many. */
    static class LazyPool {
        private static LazyPool instance;
        private LazyPool() { heavySetup(); }
        static LazyPool getInstance() {
            if (instance == null) {
                instance = new LazyPool();
            }
            return instance;
        }
    }

    /** Step 4: eager -- created during class initialisation, which the JVM makes thread-safe. */
    static class EagerPool {
        private static final EagerPool instance = new EagerPool();
        private EagerPool() {
            heavySetup();
            System.out.println("  [EagerPool constructed]");
        }
        static EagerPool getInstance() { return instance; }
        static void sayHello() { System.out.println("  Hello"); }
    }

    /** Step 5: lock without a second check -- the waiting thread still creates its own. */
    static class SyncNoRecheckPool {
        private static SyncNoRecheckPool instance;
        private SyncNoRecheckPool() { heavySetup(); }
        static SyncNoRecheckPool getInstance() {
            if (instance == null) {
                synchronized (SyncNoRecheckPool.class) {
                    instance = new SyncNoRecheckPool();
                }
            }
            return instance;
        }
    }

    /** Step 6: Double-Checked Locking with volatile -- the final version (same as DBConnectionPool). */
    static class DclPool {
        private static volatile DclPool instance;
        private DclPool() { heavySetup(); }
        static DclPool getInstance() {
            if (instance == null) {
                synchronized (DclPool.class) {
                    if (instance == null) {
                        instance = new DclPool();
                    }
                }
            }
            return instance;
        }
    }

    /** Starts 8 threads at the same instant and returns how many objects were constructed. */
    static int raceWith8Threads(Callable<Object> getInstance) throws Exception {
        created.set(0);
        ExecutorService executor = Executors.newFixedThreadPool(8);
        CountDownLatch startGate = new CountDownLatch(1);
        List<Future<Object>> futures = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            futures.add(executor.submit(() -> {
                startGate.await();
                return getInstance.call();
            }));
        }
        startGate.countDown();
        for (Future<Object> f : futures) {
            f.get();
        }
        executor.shutdown();
        return created.get();
    }

    public static void main(String[] args) throws Exception {
        System.out.println("=== Step 1: public constructor ===");
        created.set(0);
        PublicConstructorPool p1 = new PublicConstructorPool();
        PublicConstructorPool p2 = new PublicConstructorPool();
        System.out.println("  p1 == p2 : " + (p1 == p2) + ", pools created: " + created.get());

        System.out.println("\n=== Step 2: static getInstance() that calls new ===");
        created.set(0);
        System.out.println("  same object? " + (AlwaysNewPool.getInstance() == AlwaysNewPool.getInstance())
                + ", pools created: " + created.get());

        System.out.println("\n=== Step 3: lazy null check ===");
        created.set(0);
        System.out.println("  single thread, same object? " + (LazyPool.getInstance() == LazyPool.getInstance())
                + ", pools created: " + created.get());
        LazyPool.instance = null;
        System.out.println("  8 threads at once -> pools created: " + raceWith8Threads(LazyPool::getInstance)
                + "  (expected 1)");

        System.out.println("\n=== Step 4: eager initialisation ===");
        created.set(0);
        System.out.println("  calling EagerPool.sayHello() -- we never asked for the pool:");
        EagerPool.sayHello();
        System.out.println("  pools created by sayHello(): " + created.get() + "  (created on first use of the class)");
        System.out.println("  8 threads at once -> extra pools created: " + raceWith8Threads(EagerPool::getInstance));

        System.out.println("\n=== Step 5: synchronized, no second check ===");
        System.out.println("  8 threads at once -> pools created: " + raceWith8Threads(SyncNoRecheckPool::getInstance)
                + "  (expected 1)");

        System.out.println("\n=== Step 6: Double-Checked Locking + volatile ===");
        System.out.println("  8 threads at once -> pools created: " + raceWith8Threads(DclPool::getInstance));
    }
}
