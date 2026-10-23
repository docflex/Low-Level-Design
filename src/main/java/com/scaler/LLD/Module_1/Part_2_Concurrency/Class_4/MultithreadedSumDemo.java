package com.scaler.LLD.Module_1.Part_2_Concurrency.Class_4;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Concurrency-4: MultithreadedSumDemo — sum 1,000,000 numbers using threads.
 *
 * Strategy: Divide the array into chunks, sum each chunk in a separate
 * Callable<Long>, collect results via Future<Long>, sum the partial results.
 *
 * Compares sequential vs multithreaded performance.
 */
public class MultithreadedSumDemo {

    static class ChunkSumTask implements Callable<Long> {
        private final int[] array;
        private final int start;
        private final int end;

        ChunkSumTask(int[] array, int start, int end) {
            this.array = array;
            this.start = start;
            this.end = end;
        }

        @Override
        public Long call() {
            long sum = 0;
            for (int i = start; i < end; i++) {
                sum += array[i];
            }
            return sum;
        }
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        int size = 1_000_000;
        int[] numbers = new int[size];
        for (int i = 0; i < size; i++) {
            numbers[i] = i + 1;
        }

        // Expected: n*(n+1)/2 = 500000500000
        long expected = (long) size * (size + 1) / 2;

        // ── Sequential ──
        long start = System.currentTimeMillis();
        long seqSum = 0;
        for (int num : numbers) {
            seqSum += num;
        }
        long seqTime = System.currentTimeMillis() - start;

        // ── Multithreaded ──
        int cores = Runtime.getRuntime().availableProcessors();
        ExecutorService pool = Executors.newFixedThreadPool(cores);
        int chunkSize = size / cores;

        start = System.currentTimeMillis();

        List<Future<Long>> futures = new ArrayList<>();
        for (int i = 0; i < cores; i++) {
            int chunkStart = i * chunkSize;
            int chunkEnd = (i == cores - 1) ? size : chunkStart + chunkSize;
            futures.add(pool.submit(new ChunkSumTask(numbers, chunkStart, chunkEnd)));
        }

        long threadSum = 0;
        for (Future<Long> future : futures) {
            threadSum += future.get();
        }
        long threadTime = System.currentTimeMillis() - start;

        pool.shutdown();
        pool.awaitTermination(5, TimeUnit.SECONDS);

        // ── Results ──
        System.out.println("=== Multithreaded Sum Demo ===");
        System.out.println("Array size:     " + size);
        System.out.println("Cores used:     " + cores);
        System.out.println("Expected sum:   " + expected);
        System.out.println("Sequential sum: " + seqSum + " (" + seqTime + "ms)");
        System.out.println("Threaded sum:   " + threadSum + " (" + threadTime + "ms)");
        System.out.println("Both correct:   " + (seqSum == expected && threadSum == expected));
    }
}
