package com.scaler.LLD.Module_1.Part_2_Concurrency.Class_1;

/**
 * Concurrency-1: NumberPrinter — A single Runnable that prints 1-100.
 *
 * Steps to create a thread in Java:
 *   1. Create a class for the unit of work.
 *   2. Implement Runnable (CAN-DO contract — "this class can be run by a thread").
 *   3. Hand the Runnable to a Thread: new Thread(runnable).
 *   4. Call thread.start() (NOT thread.run() — see StartVsRunDemo).
 */
public class NumberPrinter implements Runnable {

    private void print() {
        String threadName = Thread.currentThread().getName();
        for (int i = 1; i <= 100; i++) {
            System.out.println("[" + threadName + "] " + i);
        }
    }

    @Override
    public void run() {
        print();
    }
}