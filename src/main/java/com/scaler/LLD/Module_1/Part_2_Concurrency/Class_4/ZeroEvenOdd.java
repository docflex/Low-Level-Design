package com.scaler.LLD.Module_1.Part_2_Concurrency.Class_4;

import java.util.concurrent.Semaphore;
import java.util.function.IntConsumer;

/**
 * Concurrency-4: Print Zero Even Odd (LeetCode 1116)
 *
 * Three semaphores enforce the ordering: 0,1,0,2,0,3,0,4,...
 *
 *   zeroSema = 1   (zero prints first)
 *   oddSema  = 0   (odd waits for zero)
 *   evenSema = 0   (even waits for zero)
 *
 * Zero thread: prints 0, then releases odd or even based on i.
 * Odd/Even threads: print their number, then release zeroSema.
 *
 * Pattern: Zero acts as coordinator, alternately unlocking odd and even.
 */
public class ZeroEvenOdd {
    private final int n;
    private final Semaphore zeroSema;
    private final Semaphore oddSema;
    private final Semaphore evenSema;

    public ZeroEvenOdd(int n) {
        this.n = n;
        this.zeroSema = new Semaphore(1);
        this.oddSema = new Semaphore(0);
        this.evenSema = new Semaphore(0);
    }

    public void zero(IntConsumer printNumber) throws InterruptedException {
        for (int i = 1; i <= n; i++) {
            zeroSema.acquire();
            printNumber.accept(0);
            if (i % 2 == 1) {
                oddSema.release();
            } else {
                evenSema.release();
            }
        }
    }

    public void even(IntConsumer printNumber) throws InterruptedException {
        for (int i = 2; i <= n; i += 2) {
            evenSema.acquire();
            printNumber.accept(i);
            zeroSema.release();
        }
    }

    public void odd(IntConsumer printNumber) throws InterruptedException {
        for (int i = 1; i <= n; i += 2) {
            oddSema.acquire();
            printNumber.accept(i);
            zeroSema.release();
        }
    }
}
