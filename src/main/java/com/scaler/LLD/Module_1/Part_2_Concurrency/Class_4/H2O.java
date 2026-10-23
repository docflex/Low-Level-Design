package com.scaler.LLD.Module_1.Part_2_Concurrency.Class_4;

import java.util.concurrent.Semaphore;

/**
 * Concurrency-4: Building H2O (LeetCode 1117)
 *
 * Two semaphores enforce the HHO ordering:
 *
 *   hydrogenSemaphore = 2  (need 2 H first)
 *   oxygenSemaphore   = 0  (O must wait for 2 H)
 *
 * Each H: acquire(1) from H-sema, print, release(1) to O-sema
 * Each O: acquire(2) from O-sema, print, release(2) to H-sema
 *
 * Cycle: H→H→O → H→H→O → ...
 */
public class H2O {
    private final Semaphore hydrogenSemaphore;
    private final Semaphore oxygenSemaphore;

    public H2O() {
        hydrogenSemaphore = new Semaphore(2);
        oxygenSemaphore = new Semaphore(0);
    }

    public void hydrogen(Runnable releaseHydrogen) throws InterruptedException {
        hydrogenSemaphore.acquire();
        releaseHydrogen.run();
        oxygenSemaphore.release();
    }

    public void oxygen(Runnable releaseOxygen) throws InterruptedException {
        oxygenSemaphore.acquire(2);
        releaseOxygen.run();
        hydrogenSemaphore.release(2);
    }
}
