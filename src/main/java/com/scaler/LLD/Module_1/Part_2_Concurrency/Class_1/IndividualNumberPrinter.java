package com.scaler.LLD.Module_1.Part_2_Concurrency.Class_1;

/**
 * Concurrency-1: IndividualNumberPrinter — One Runnable per number.
 *
 * Since run() takes no arguments, we store the value as a field.
 * The field is private final because it's set once and never changes
 * (same immutability principle from LLD 3-5).
 *
 * In ThreadDemo, 100 of these are created — one per number —
 * and each is handed to its own Thread. This produces 101 application
 * threads (100 workers + 1 main). Output order is non-deterministic.
 */
public class IndividualNumberPrinter implements Runnable {

    private final int value;

    public IndividualNumberPrinter(int value) {
        this.value = value;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " is printing: " + this.value);
    }
}
