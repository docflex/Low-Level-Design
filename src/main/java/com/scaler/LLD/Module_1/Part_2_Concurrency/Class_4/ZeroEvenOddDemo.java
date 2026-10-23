package com.scaler.LLD.Module_1.Part_2_Concurrency.Class_4;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Concurrency-4: ZeroEvenOddDemo — runnable demo for Print Zero Even Odd.
 *
 * For n=5, expected output: 0102030405
 */
public class ZeroEvenOddDemo {
    public static void main(String[] args) throws InterruptedException {
        int n = 5;
        System.out.println("=== Print Zero Even Odd Demo (n=" + n + ") ===");
        System.out.println("Expected: 0102030405\n");

        ZeroEvenOdd zeo = new ZeroEvenOdd(n);
        StringBuilder output = new StringBuilder();

        ExecutorService pool = Executors.newFixedThreadPool(3);

        pool.execute(() -> {
            try { zeo.zero(output::append); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        });
        pool.execute(() -> {
            try { zeo.odd(output::append); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        });
        pool.execute(() -> {
            try { zeo.even(output::append); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        });

        pool.shutdown();
        pool.awaitTermination(5, TimeUnit.SECONDS);

        System.out.println("Actual:   " + output);
        System.out.println("Correct:  " + output.toString().equals("0102030405"));
    }
}
