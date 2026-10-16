package com.scaler.LLD.Module_1.Part_2_Concurrency.Class_3;

import java.util.concurrent.Callable;

/**
 * Concurrency-3: SubtractorTask — subtracts 1+2+...+100 from the shared Counter.
 *
 * Mirror of AdderTask. When both run on the same Counter starting at 0,
 * the final value should be 0 (since +1..+100 and -1..-100 cancel out).
 * Without synchronization, the result is non-deterministic due to race conditions.
 */
public class SubtractorTask implements Callable<Void> {
    private final Counter counter;

    public SubtractorTask(Counter counter) {
        this.counter = counter;
    }

    @Override
    public Void call() throws Exception {
        for (int i = 1; i <= 100; i++) {
            counter.decrement(i);
        }
        return null;
    }
}
