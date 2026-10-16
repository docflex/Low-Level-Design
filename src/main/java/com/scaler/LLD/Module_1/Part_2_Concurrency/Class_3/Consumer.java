package com.scaler.LLD.Module_1.Part_2_Concurrency.Class_3;

/**
 * Concurrency-3: Consumer — removes items from the shared Store in a loop.
 *
 * Runs until the thread is interrupted.
 */
public class Consumer implements Runnable {
    private final Store store;

    public Consumer(Store store) {
        this.store = store;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            this.store.removeItem();
        }
    }
}
