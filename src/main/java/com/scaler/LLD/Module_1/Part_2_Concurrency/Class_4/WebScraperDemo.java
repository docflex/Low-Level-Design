package com.scaler.LLD.Module_1.Part_2_Concurrency.Class_4;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Concurrency-4: WebScraperDemo — Fixed vs Cached ThreadPool for IO-bound tasks.
 *
 * Simulates fetching 20 URLs, each taking ~200ms (IO wait).
 * Compares:
 *   1. FixedThreadPool(cores)  — threads = CPU cores (bad for IO)
 *   2. CachedThreadPool        — threads scale to demand (good for IO)
 *
 * Why Cached wins for IO-bound work:
 *   With FixedThreadPool(4 cores), only 4 fetches run in parallel.
 *   20 URLs / 4 threads = 5 rounds * 200ms = ~1000ms
 *
 *   With CachedThreadPool, all 20 fetches run in parallel.
 *   20 URLs / 20 threads = 1 round * 200ms = ~200ms
 */
public class WebScraperDemo {

    static class FetchUrlTask implements Callable<String> {
        private final String url;

        FetchUrlTask(String url) {
            this.url = url;
        }

        @Override
        public String call() throws Exception {
            Thread.sleep(200); // Simulate network IO latency
            return "Fetched: " + url + " [" + Thread.currentThread().getName() + "]";
        }
    }

    private static long benchmark(ExecutorService pool, List<FetchUrlTask> tasks)
            throws InterruptedException, ExecutionException {
        long start = System.currentTimeMillis();

        List<Future<String>> futures = new ArrayList<>();
        for (FetchUrlTask task : tasks) {
            futures.add(pool.submit(task));
        }
        for (Future<String> f : futures) {
            f.get();
        }

        long elapsed = System.currentTimeMillis() - start;
        pool.shutdown();
        pool.awaitTermination(5, TimeUnit.SECONDS);
        return elapsed;
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        int urlCount = 20;
        List<FetchUrlTask> tasks = new ArrayList<>();
        for (int i = 1; i <= urlCount; i++) {
            tasks.add(new FetchUrlTask("https://example.com/page/" + i));
        }

        int cores = Runtime.getRuntime().availableProcessors();

        // ── Fixed Thread Pool (core count) ──
        List<FetchUrlTask> tasks1 = new ArrayList<>();
        for (int i = 1; i <= urlCount; i++) {
            tasks1.add(new FetchUrlTask("https://example.com/page/" + i));
        }
        long fixedTime = benchmark(Executors.newFixedThreadPool(cores), tasks1);

        // ── Cached Thread Pool ──
        List<FetchUrlTask> tasks2 = new ArrayList<>();
        for (int i = 1; i <= urlCount; i++) {
            tasks2.add(new FetchUrlTask("https://example.com/page/" + i));
        }
        long cachedTime = benchmark(Executors.newCachedThreadPool(), tasks2);

        // ── Results ──
        System.out.println("=== Web Scraper Benchmark ===");
        System.out.println("URLs to fetch:    " + urlCount);
        System.out.println("IO latency/URL:   200ms (simulated)");
        System.out.println("Available cores:  " + cores);
        System.out.println();
        System.out.println("FixedThreadPool(" + cores + "):  " + fixedTime + "ms");
        System.out.println("CachedThreadPool:     " + cachedTime + "ms");
        System.out.println("Speedup:              ~" + String.format("%.1f", (double) fixedTime / cachedTime) + "x");
        System.out.println();
        System.out.println("Why? Threads in FixedPool(" + cores + ") block on IO. Only " + cores + " URLs");
        System.out.println("fetch at a time. CachedPool scales to " + urlCount + " threads, all fetching in parallel.");
    }
}
