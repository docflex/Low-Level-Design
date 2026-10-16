package com.scaler.LLD.Module_1.Part_2_Concurrency.Class_3;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Concurrency-3: ProdConDemo — Producer-Consumer with synchronized Store.
 *
 * 5 producers and 5 consumers share a Store with capacity 10.
 * Runs for 1 second to show the pattern, then shuts down.
 *
 * With synchronized addItem()/removeItem():
 *   - Size never exceeds maxSize (no overflow)
 *   - Size never goes below 0 (no underflow / IndexOutOfBoundsException)
 *
 * FIX APPLIED: Original ran forever with no shutdown. Now runs for
 *   1 second, then interrupts all threads via shutdownNow().
 *   Producer/Consumer check Thread.interrupted() so they stop cleanly.
 */
public class ProdConDemo {
    public static void main(String[] args) throws InterruptedException {
        Store store = new Store(10);

        ExecutorService executorService = Executors.newCachedThreadPool();

        // 5 Producers
        for (int i = 1; i <= 5; i++) {
            executorService.execute(new Producer(store));
        }

        // 5 Consumers
        for (int i = 1; i <= 5; i++) {
            executorService.execute(new Consumer(store));
        }

        // Let it run for 1 second to see the pattern
        Thread.sleep(1000);

        System.out.println("\n--- Shutting down (store size: " + store.getItemCount() + ") ---");
        executorService.shutdownNow();
        executorService.awaitTermination(2, TimeUnit.SECONDS);
    }
}
