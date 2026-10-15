package com.scaler.LLD.Module_1.Part_2_Concurrency.Class_2;

import java.util.concurrent.Callable;

/**
 * Concurrency-2: NumberMultiplier — Callable that returns a result.
 *
 * Runnable.run() returns void. When you need a return value,
 * use Callable<T>.call() which returns T.
 *
 * The sleep simulates a slow computation (e.g., network call, DB query).
 * Kept at 2s so demos finish quickly. In production, the delay would
 * come from real work, not Thread.sleep().
 */
public class NumberMultiplier implements Callable<Integer> {

    private final int number;
    private final long simulatedDelayMs;

    public NumberMultiplier(int number, long simulatedDelayMs) {
        this.number = number;
        this.simulatedDelayMs = simulatedDelayMs;
    }

    // Convenience: default 2-second delay for demos
    public NumberMultiplier(int number) {
        this(number, 2000);
    }

    @Override
    public Integer call() throws Exception {
        Thread.sleep(simulatedDelayMs);
        return this.number * 5;
    }
}
