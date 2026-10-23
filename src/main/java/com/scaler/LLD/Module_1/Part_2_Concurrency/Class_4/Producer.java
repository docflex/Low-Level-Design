package com.scaler.LLD.Module_1.Part_2_Concurrency.Class_4;

import java.util.concurrent.Semaphore;

/**
 * Concurrency-4: Producer — adds items using semaphore-based flow control.
 *
 * Flow:
 *   1. producerSemaphore.acquire()  — wait for an empty slot (P--)
 *   2. store.addItem()              — add item (lock protects the list)
 *   3. consumerSemaphore.release()  — signal that an item is available (C++)
 *
 * Respects interruption for clean shutdown.
 */
public class Producer implements Runnable {
    private final Store store;
    private final Semaphore producerSemaphore;
    private final Semaphore consumerSemaphore;

    public Producer(Store store, Semaphore producerSemaphore, Semaphore consumerSemaphore) {
        this.store = store;
        this.producerSemaphore = producerSemaphore;
        this.consumerSemaphore = consumerSemaphore;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                producerSemaphore.acquire();       // P-- (blocks if no empty slots)
                this.store.addItem(new Object());
                consumerSemaphore.release();       // C++ (signal item available)
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Restore interrupt flag
                break;                             // Exit loop cleanly
            }
        }
    }
}
