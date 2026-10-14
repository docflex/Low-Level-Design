package com.scaler.LLD.Module_1.Part_2_Concurrency.Class_2;

/**
 * Concurrency-2: IndividualNumberPrinter — One Runnable per number.
 *
 * Copy in Class_2 package for BenchmarkingDemo (raw threads vs pool).
 * Same implementation as Class_1 version.
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
