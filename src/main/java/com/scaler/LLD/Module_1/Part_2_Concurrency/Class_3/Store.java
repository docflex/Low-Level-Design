package com.scaler.LLD.Module_1.Part_2_Concurrency.Class_3;

import java.util.ArrayList;
import java.util.List;

/**
 * Concurrency-3: Store — bounded buffer for Producer-Consumer.
 *
 * addItem() and removeItem() are synchronized to prevent race conditions
 * (e.g., two producers reading size=9 with max=10, both passing the check,
 * resulting in 11 items — or two consumers reading size=1, both passing,
 * causing IndexOutOfBoundsException).
 *
 * LIMITATION: This is a busy-wait solution. When the store is full,
 * producers silently skip (wasting CPU in while(true)). When empty,
 * consumers silently skip. This waste is solved by Semaphores (next class).
 *
 * FIX APPLIED: Removed unsafe setMaxSize()/setItems()/getItems() which
 * allowed external code to bypass synchronization. maxSize is now final.
 */
public class Store {
    private final int maxSize;
    private final List<Object> items;

    public Store(int maxSize) {
        this.maxSize = maxSize;
        this.items = new ArrayList<>(this.maxSize);
    }

    public synchronized int getItemCount() {
        return items.size();
    }

    public int getMaxSize() {
        return maxSize;
    }

    public synchronized void addItem(Object item) {
        if (this.items.size() < this.maxSize) {
            items.add(item);
            System.out.println(Thread.currentThread().getName()
                    + " produced | size: " + items.size());
        }
    }

    public synchronized void removeItem() {
        if (this.items.size() > 0) {
            items.remove(items.size() - 1);
            System.out.println(Thread.currentThread().getName()
                    + " consumed | size: " + items.size());
        }
    }
}
