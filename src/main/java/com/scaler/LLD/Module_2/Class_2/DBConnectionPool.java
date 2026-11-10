package com.scaler.LLD.Module_2.Class_2;

import java.util.ArrayList;
import java.util.List;

/**
 * Final Singleton from the class: lazy, thread-safe Double-Checked Locking.
 *
 * - private constructor  -> nobody outside can call new
 * - static getInstance() -> callable without an object
 * - volatile instance    -> no thread can see a half-constructed pool (see Slide 14)
 * - check, lock, check   -> only the first thread creates it; later calls skip the lock
 *
 * See SingletonEvolutionDemo for every broken step on the way here.
 */
public class DBConnectionPool {
    private final String url;
    private final String password;
    private final List<String> connections;
    private final int maxPoolSize;

    private static volatile DBConnectionPool instance;

    private DBConnectionPool(String url, String password, int maxPoolSize) {
        this.url = url;
        this.password = password;
        this.maxPoolSize = maxPoolSize;
        this.connections = new ArrayList<>(maxPoolSize);
        System.out.println("  [DBConnectionPool constructed by " + Thread.currentThread().getName() + "]");
    }

    public static DBConnectionPool getInstance() {
        if (instance == null) {                          // 1st check: no lock once initialised
            synchronized (DBConnectionPool.class) {
                if (instance == null) {                  // 2nd check: another thread may have won
                    instance = new DBConnectionPool("url", "pass", 3);
                }
            }
        }
        return instance;
    }

    /** Unlike the eager version, calling this does NOT create the pool. */
    public static void sayHello() {
        System.out.println("  Hello");
    }

    public String getUrl() {
        return url;
    }

    public int getMaxPoolSize() {
        return maxPoolSize;
    }
}
