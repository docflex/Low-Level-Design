package com.scaler.LLD.Module_2.Class_2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/** Uses the final DBConnectionPool (lazy + Double-Checked Locking + volatile). */
public class SingletonClient {
    public static void main(String[] args) throws Exception {
        System.out.println("=== 1. Static method does not create the lazy pool ===");
        DBConnectionPool.sayHello();

        System.out.println("\n=== 2. getInstance() twice -> same object ===");
        DBConnectionPool dbConnectionPool1 = DBConnectionPool.getInstance();
        DBConnectionPool dbConnectionPool2 = DBConnectionPool.getInstance();
        System.out.println("  pool1 == pool2 : " + (dbConnectionPool1 == dbConnectionPool2));
        System.out.println("  url=" + dbConnectionPool1.getUrl() + ", maxPoolSize=" + dbConnectionPool1.getMaxPoolSize());

        System.out.println("\n=== 3. 8 threads call getInstance() at the same moment ===");
        ExecutorService executor = Executors.newFixedThreadPool(8);
        CountDownLatch startGate = new CountDownLatch(1);
        List<Future<DBConnectionPool>> futures = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            futures.add(executor.submit(() -> {
                startGate.await();
                return DBConnectionPool.getInstance();
            }));
        }
        startGate.countDown();
        Set<DBConnectionPool> distinct = Collections.newSetFromMap(new IdentityHashMap<>());
        for (Future<DBConnectionPool> f : futures) {
            distinct.add(f.get());
        }
        executor.shutdown();
        System.out.println("  distinct instances seen by 8 threads: " + distinct.size());
        System.out.println("  same as main thread's pool: " + distinct.contains(dbConnectionPool1));
    }
}
