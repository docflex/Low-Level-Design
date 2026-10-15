package com.scaler.LLD.Module_1.Part_2_Concurrency.Class_2;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * Concurrency-2: MergeSortTask — Multithreaded Merge Sort using Callable.
 *
 * Each recursive call submits left and right halves as separate tasks
 * to the shared ExecutorService, then blocks on Future.get() to
 * collect sorted results before merging.
 *
 * DEADLOCK WARNING (Fixed in SortingMultithreadedDemo):
 *   With a FixedThreadPool(2), this DEADLOCKS. The parent task holds
 *   a thread and waits (via .get()) for child tasks. But the child tasks
 *   need threads too — and the pool is exhausted. No child completes,
 *   so the parent waits forever. This is called THREAD STARVATION DEADLOCK.
 *
 *   Fix: Use a CachedThreadPool (unbounded threads) for recursive
 *   task submission. See SortingMultithreadedDemo for the corrected version.
 *
 * FIX APPLIED: Base case handles size <= 1 (not just == 1)
 *   to guard against empty sublists.
 */
public class MergeSortTask implements Callable<List<Integer>> {

    private final List<Integer> list;
    private final ExecutorService executorService;

    public MergeSortTask(List<Integer> list, ExecutorService executorService) {
        this.list = list;
        this.executorService = executorService;
    }

    @Override
    public List<Integer> call() throws Exception {
        if (list.size() <= 1) {
            return new ArrayList<>(list);
        }

        int mid = list.size() >> 1;

        List<Integer> leftHalf = list.subList(0, mid);
        List<Integer> rightHalf = list.subList(mid, list.size());

        MergeSortTask leftSortingTask = new MergeSortTask(leftHalf, executorService);
        MergeSortTask rightSortingTask = new MergeSortTask(rightHalf, executorService);

        Future<List<Integer>> leftSortedHalfFuture = executorService.submit(leftSortingTask);
        Future<List<Integer>> rightSortedHalfFuture = executorService.submit(rightSortingTask);

        List<Integer> leftSortedHalf = leftSortedHalfFuture.get();
        List<Integer> rightSortedHalf = rightSortedHalfFuture.get();

        return merge(leftSortedHalf, rightSortedHalf);
    }

    private List<Integer> merge(List<Integer> leftHalf, List<Integer> rightHalf) {
        int leftIndex = 0, rightIndex = 0;
        List<Integer> sortedList = new ArrayList<>();

        while (leftIndex < leftHalf.size() && rightIndex < rightHalf.size()) {
            if (leftHalf.get(leftIndex) <= rightHalf.get(rightIndex)) {
                sortedList.add(leftHalf.get(leftIndex));
                leftIndex++;
            } else {
                sortedList.add(rightHalf.get(rightIndex));
                rightIndex++;
            }
        }

        while (leftIndex < leftHalf.size()) {
            sortedList.add(leftHalf.get(leftIndex));
            leftIndex++;
        }

        while (rightIndex < rightHalf.size()) {
            sortedList.add(rightHalf.get(rightIndex));
            rightIndex++;
        }

        return sortedList;
    }
}
