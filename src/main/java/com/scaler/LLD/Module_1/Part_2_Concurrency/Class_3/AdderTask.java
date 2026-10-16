package com.scaler.LLD.Module_1.Part_2_Concurrency.Class_3;

import java.util.concurrent.Callable;

/**
 * Concurrency-3: AdderTask — adds 1+2+...+100 to the shared Counter.
 *
 * Uses Callable<Void> so that submit() returns a Future, allowing
 * the main thread to block via Future.get() until this task completes.
 *
 * NOTE: submit(Runnable) ALSO returns a Future<?> — you don't strictly
 * need Callable just for blocking. Callable is specifically for returning
 * a value. Here Callable<Void> is used for consistency with the lesson.
 */
public class AdderTask implements Callable<Void> {
    private final Counter counter;

    public AdderTask(Counter counter) {
        this.counter = counter;
    }

    @Override
    public Void call() throws Exception {
        for (int i = 1; i <= 100; i++) {
            counter.increment(i);
        }
        return null;
    }
}
