package com.scaler.LLD.Fundamentals.Class_2;

/**
 * LLD 2 — OOP-1: Static Block Execution Order
 *
 * Demonstrates that:
 *   1. Static blocks run BEFORE constructors.
 *   2. Static blocks run EXACTLY ONCE (when the class is first loaded).
 *   3. Static variables are shared across all instances.
 *
 * MEMORY NOTE (Java 8+):
 *   Static variables are stored on the JAVA HEAP, as part of the
 *   java.lang.Class<Config> object created at class loading time.
 *   Common wrong answer: "on the stack."
 *
 * COMPILE TIME vs RUN TIME:
 *   - Compile time: `javac` translates .java → .class (bytecode).
 *     Catches syntax errors, type mismatches, missing imports.
 *   - Run time: `java` executes the .class on the JVM.
 *     Objects created, methods called, exceptions thrown, GC runs.
 *   - Class loading (early run time): JVM loads the class →
 *     static blocks execute → static variables initialized →
 *     then constructors can be called.
 *
 * Run: Right-click → Run 'StaticOrderDemo.main()'
 */
public class StaticOrderDemo {

    // ══════════════════════════════════════════════════════════════════
    //  A class with a static block simulating config loading
    // ══════════════════════════════════════════════════════════════════

    static class Config {
        static String dbUrl;
        static int maxConnections;
        static int instanceCount;

        // Static block — runs ONCE when Config is first loaded.
        // Use case: loading configuration from a file, environment, etc.
        static {
            System.out.println("  [1] Static block: loading configuration...");
            dbUrl = "jdbc:mysql://localhost:3306/scaler";
            maxConnections = 10;
            instanceCount = 0;
            System.out.println("      DB URL         = " + dbUrl);
            System.out.println("      Max connections = " + maxConnections);
            System.out.println("      (This runs BEFORE any constructor.)");
        }

        String label;

        Config(String label) {
            this.label = label;
            Config.instanceCount++;
            System.out.println("  [" + (Config.instanceCount + 1)
                    + "] Constructor: created '" + label
                    + "' (instance #" + Config.instanceCount + ")");
        }

        @Override
        public String toString() {
            return "Config{label='" + label + "', dbUrl='" + dbUrl
                    + "', maxConn=" + maxConnections + "}";
        }
    }


    // ══════════════════════════════════════════════════════════════════
    //  DEMO: Multiple static blocks execute in source order
    // ══════════════════════════════════════════════════════════════════

    static class MultiBlockDemo {
        static String phase;

        static {
            phase = "ALPHA";
            System.out.println("  Block 1: phase = " + phase);
        }

        static {
            phase = "BETA";
            System.out.println("  Block 2: phase = " + phase);
        }

        static {
            phase = "RELEASE";
            System.out.println("  Block 3: phase = " + phase);
        }
    }


    // ══════════════════════════════════════════════════════════════════
    //  MAIN
    // ══════════════════════════════════════════════════════════════════

    public static void main(String[] args) {

        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.println("║  Static Block Execution Order Demo              ║");
        System.out.println("╚══════════════════════════════════════════════════╝");

        System.out.println("\n── Config class: first access triggers class loading ──");
        System.out.println("  (main() starts here. Static block runs before first `new`.)\n");

        Config c1 = new Config("Primary");
        Config c2 = new Config("Replica");
        Config c3 = new Config("Analytics");

        System.out.println("\n  Total instances: " + Config.instanceCount);   // 3
        System.out.println("  All share:       " + Config.dbUrl);             // same URL
        System.out.println("  c1 = " + c1);
        System.out.println("  c2 = " + c2);
        System.out.println("  c3 = " + c3);


        System.out.println("\n── Multiple static blocks run in source order ──\n");
        // Just accessing the class triggers loading
        System.out.println("  Final phase = " + MultiBlockDemo.phase);   // RELEASE


        // ══════════════════════════════════════════════════════════════
        //  EXECUTION ORDER SUMMARY
        // ══════════════════════════════════════════════════════════════

        System.out.println("\n╔══════════════════════════════════════════════════╗");
        System.out.println("║  Execution Order Summary                         ║");
        System.out.println("╚══════════════════════════════════════════════════╝");
        System.out.println("  1. JVM loads the .class file into memory.");
        System.out.println("  2. Static blocks execute (once, in source order).");
        System.out.println("  3. Static variables are initialized.");
        System.out.println("  4. main() begins execution.");
        System.out.println("  5. `new ClassName()` calls the constructor.");
        System.out.println("  6. Each `new` creates a separate object on the heap.");
        System.out.println("  7. Static variables are shared — NOT per-object.");
    }
}
