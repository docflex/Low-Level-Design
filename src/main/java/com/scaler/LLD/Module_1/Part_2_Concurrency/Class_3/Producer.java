package com.scaler.LLD.Module_1.Part_2_Concurrency.Class_3;

/**
 * Concurrency-3: Producer — adds items to the shared Store in a loop.
 *
 * Runs until the thread is interrupted (respects Thread.interrupted()
 * so the executor can shut it down cleanly).
 */
public class Producer implements Runnable {
    private final Store store;

    public Producer(Store store) {
        this.store = store;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            this.store.addItem(new Object());
        }
    }
}
