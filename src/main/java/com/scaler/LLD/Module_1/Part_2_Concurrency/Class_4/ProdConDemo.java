package com.scaler.LLD.Module_1.Part_2_Concurrency.Class_4;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * Concurrency-4: ProdConDemo — Producer-Consumer with Semaphores + Lock.
 *
 * producerSemaphore starts at N (empty slots).
 * consumerSemaphore starts at 0 (nothing to consume).
 *
 * Invariant: producerSema.permits + consumerSema.permits = N
 *   (permits shift from producer to consumer as items are produced,
 *    and back as items are consumed).
 *
 * FIX APPLIED: Runs for 1 second then shuts down cleanly.
 */
public class ProdConDemo {
    public static void main(String[] args) throws InterruptedException {
        Store store = new Store(10);
        Semaphore producerSemaphore = new Semaphore(10); // 10 empty slots
        Semaphore consumerSemaphore = new Semaphore(0);  // 0 items to consume

        ExecutorService executorService = Executors.newCachedThreadPool();

        // 5 Producers
        for (int i = 1; i <= 5; i++) {
            executorService.execute(new Producer(store, producerSemaphore, consumerSemaphore));
        }

        // 5 Consumers
        for (int i = 1; i <= 5; i++) {
            executorService.execute(new Consumer(store, producerSemaphore, consumerSemaphore));
        }

        Thread.sleep(1000);

        System.out.println("\n--- Shutting down (store size: " + store.getItemCount() + ") ---");
        executorService.shutdownNow();
        executorService.awaitTermination(2, TimeUnit.SECONDS);
    }
}
