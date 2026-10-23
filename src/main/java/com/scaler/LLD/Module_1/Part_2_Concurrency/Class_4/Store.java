package com.scaler.LLD.Module_1.Part_2_Concurrency.Class_4;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Concurrency-4: Store — bounded buffer with Semaphore + Lock.
 *
 * Semaphores control HOW MANY threads can enter (parallelism).
 * The ReentrantLock ensures the list operations are atomic (consistency).
 *
 * No size checks needed — semaphores guarantee producers won't enter
 * when full and consumers won't enter when empty.
 *
 * FIX APPLIED: lock/unlock now in try-finally to prevent deadlock
 * if items.add() or System.out throws an exception.
 */
public class Store {
    private final int maxSize;
    private final List<Object> items;
    private final Lock lock = new ReentrantLock();

    public Store(int maxSize) {
        this.maxSize = maxSize;
        this.items = new ArrayList<>(maxSize);
    }

    public int getMaxSize() {
        return maxSize;
    }

    public int getItemCount() {
        lock.lock();
        try {
            return items.size();
        } finally {
            lock.unlock();
        }
    }

    public void addItem(Object item) {
        lock.lock();
        try {
            items.add(item);
            System.out.println(Thread.currentThread().getName()
                    + " produced | size: " + items.size());
        } finally {
            lock.unlock();
        }
    }

    public void removeItem() {
        lock.lock();
        try {
            items.remove(items.size() - 1);
            System.out.println(Thread.currentThread().getName()
                    + " consumed | size: " + items.size());
        } finally {
            lock.unlock();
        }
    }
}
