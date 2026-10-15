package com.scaler.LLD.Module_1.Part_2_Concurrency.Class_2;

import java.util.List;
import java.util.concurrent.*;

/**
 * Concurrency-2: SortingMultithreadedDemo — Multithreaded Merge Sort
 *
 * FIX APPLIED — DEADLOCK:
 *   Original used FixedThreadPool(2). With recursive Merge Sort, the parent
 *   task occupies a thread and waits for child tasks via Future.get().
 *   But child tasks need threads too — with only 2 threads, both are held
 *   by parent tasks waiting on children that can never start.
 *   This is THREAD STARVATION DEADLOCK.
 *
 *   Fix: Use CachedThreadPool — it creates new threads as needed,
 *   so child tasks always get a thread. Idle threads are cleaned up
 *   after 60 seconds.
 *
 * CORRECTION: The script calls this an "IO-bound" problem (line 756).
 *   Merge Sort is CPU-bound. The real issue is RECURSIVE TASK SUBMISSION
 *   where parent tasks hold threads while waiting for child tasks.
 *   CachedThreadPool solves this by having no thread limit.
 */
public class SortingMultithreadedDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        List<Integer> unsortedList = List.of(9, 8, 7, 6, 5, 4, 3, 2, 1);

        // CachedThreadPool: threads created on demand, reused when idle, cleaned up after 60s
        // Required here because recursive tasks submit sub-tasks that also need threads
        ExecutorService executorService = Executors.newCachedThreadPool();

        System.out.println("Unsorted List: " + unsortedList);

        MergeSortTask mergeSortTask = new MergeSortTask(unsortedList, executorService);

        Future<List<Integer>> sortedListFuture = executorService.submit(mergeSortTask);
        List<Integer> sortedList = sortedListFuture.get();

        System.out.println("Sorted List:   " + sortedList);

        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.SECONDS);
    }
}
