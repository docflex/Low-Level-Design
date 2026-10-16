package com.scaler.LLD.Module_1.Part_2_Concurrency.Class_3;

/**
 * Concurrency-3: Counter — shared mutable state.
 *
 * This is the shared object that Adder and Subtractor both modify.
 *
 * increment() and decrement() are synchronized methods:
 *   - They acquire the intrinsic lock on 'this' (this Counter instance)
 *   - Only one thread can execute ANY synchronized method on the same
 *     Counter at a time (add and sub are mutually exclusive)
 *   - The lock is automatically released even if an exception occurs
 *
 * getX() is also synchronized for visibility — without it, the main
 * thread could read a stale cached value after Future.get() returns.
 * (Future.get() provides happens-before, but making getX() synchronized
 * keeps Counter independently thread-safe.)
 */
public class Counter {
    private int x;

    public Counter(int x) {
        this.x = x;
    }

    public synchronized int getX() {
        return x;
    }

    public synchronized void increment(int num) {
        this.x = this.x + num;
    }

    public synchronized void decrement(int num) {
        this.x = this.x - num;
    }
}
