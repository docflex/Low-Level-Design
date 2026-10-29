package com.scaler.LLD.Module_1.Part_3_Advanced_Java.Class_4;

import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * Exception Propagation Chain demo.
 * Shows how a checked exception bubbles up through the call stack:
 *   f2() -> f1() -> main()
 * Each method can either handle it or declare "throws" to propagate.
 * Here main() handles it gracefully (the "CEO" catches the issue).
 */
public class Chaining {
    public static void main(String[] args) {
        System.out.println("=== Exception Propagation Chain ===");
        System.out.println("A (main starts)");

        // main (the "CEO") decides to handle it instead of crashing
        try {
            f1();
            System.out.println("B (this won't print -- exception thrown before)");
        } catch (FileNotFoundException e) {
            System.out.println("Caught in main: " + e.getMessage());
            System.out.println("  f2() threw -> f1() propagated -> main() handled");
        }

        System.out.println("C (main continues after handling)");

        System.out.println("\n=== Key takeaway ===");
        System.out.println("  Each method has 2 choices: handle (try-catch) or propagate (throws)");
        System.out.println("  If main() also throws, JVM is last resort -> program crashes");
    }

    /** f1 propagates to caller (doesn't handle). */
    public static void f1() throws FileNotFoundException {
        f2();
    }

    /** f2 throws a checked exception (FileReader constructor). */
    public static void f2() throws FileNotFoundException {
        FileReader fr = new FileReader("sample.txt");  // file doesn't exist -> FNFE
    }
}
