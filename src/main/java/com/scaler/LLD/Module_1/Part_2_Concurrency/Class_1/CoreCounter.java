package com.scaler.LLD.Module_1.Part_2_Concurrency.Class_1;

/**
 * Concurrency-1: CoreCounter
 *
 * Prints the number of available CPU cores (logical processors).
 *
 * Note: On CPUs with Hyper-Threading (e.g., i7 8-core + HT),
 * this returns logical processors (e.g., 16), NOT physical cores.
 * Two logical processors share one physical core's resources,
 * so the performance gain from HT is ~15-30%, not 2x.
 */
public class CoreCounter {
    public static void main(String[] args) {
        int cores = Runtime.getRuntime().availableProcessors();
        System.out.println("Available logical processors: " + cores);
        System.out.println("(Physical cores may be fewer if Hyper-Threading is enabled)");
    }
}
