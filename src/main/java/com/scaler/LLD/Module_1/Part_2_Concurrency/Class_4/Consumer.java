package com.scaler.LLD.Module_1.Part_2_Concurrency.Class_4;

import java.util.concurrent.Semaphore;

/**
 * Concurrency-4: Consumer — removes items using semaphore-based flow control.
 *
 * Flow:
 *   1. consumerSemaphore.acquire()  — wait for an available item (C--)
 *   2. store.removeItem()           — remove item (lock protects the list)
 *   3. producerSemaphore.release()  — signal that a slot is now empty (P++)
 */
public class Consumer implements Runnable {
    private final Store store;
    private final Semaphore producerSemaphore;
    private final Semaphore consumerSemaphore;

    public Consumer(Store store, Semaphore producerSemaphore, Semaphore consumerSemaphore) {
        this.store = store;
        this.producerSemaphore = producerSemaphore;
        this.consumerSemaphore = consumerSemaphore;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                consumerSemaphore.acquire();       // C-- (blocks if nothing to consume)
                this.store.removeItem();
                producerSemaphore.release();       // P++ (signal slot freed)
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
