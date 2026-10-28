package com.scaler.LLD.Module_1.Part_3_Advanced_Java.Class_3;

import java.io.FileReader;
import java.io.FileNotFoundException;

/**
 * Java Advanced Concepts - 3 [Streams and Lambdas]
 *
 * Intro to Exception Handling:
 * - Errors vs Exceptions
 * - Unchecked (RuntimeException): preventable with logic
 * - Checked (e.g., FileNotFoundException): compiler forces try-catch
 */
public class Client2 {
    public static void main(String[] args) {

        // ── 1. Unchecked Exception: ArithmeticException ──
        System.out.println("=== Unchecked Exception (ArithmeticException) ===");

        int x = 10;
        int y = 0;

        // Prevention approach: check before dividing
        if (y == 0) {
            System.out.println("  Prevented: Y Cannot Be 0");
        } else {
            System.out.println("  " + x + " / " + y + " = " + (x / y));
        }

        // What happens without prevention:
        try {
            int result = x / y;
            System.out.println("  Result: " + result);
        } catch (ArithmeticException e) {
            System.out.println("  Caught ArithmeticException: " + e.getMessage());
        }

        // ── 2. Unchecked Exception: NullPointerException ──
        System.out.println("\n=== Unchecked Exception (NullPointerException) ===");

        String str = null;
        try {
            System.out.println("  Length: " + str.length());
        } catch (NullPointerException e) {
            System.out.println("  Caught NullPointerException: " + e.getMessage());
        }

        // ── 3. Unchecked Exception: ArrayIndexOutOfBoundsException ──
        System.out.println("\n=== Unchecked Exception (ArrayIndexOutOfBounds) ===");

        int[] arr = {1, 2, 3};
        try {
            System.out.println("  arr[5] = " + arr[5]);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("  Caught: " + e.getMessage());
        }

        // ── 4. Checked Exception: FileNotFoundException ──
        System.out.println("\n=== Checked Exception (FileNotFoundException) ===");
        System.out.println("  Compiler FORCES try-catch for checked exceptions.");

        // Without try-catch, this line won't even compile:
        try {
            FileReader r = new FileReader("NonExistentFile.txt");
            System.out.println("  File opened: " + r);
        } catch (FileNotFoundException e) {
            System.out.println("  Caught FileNotFoundException: " + e.getMessage());
        }

        System.out.println("\n=== Summary ===");
        System.out.println("  Errors: Unrecoverable (OutOfMemoryError, StackOverflowError)");
        System.out.println("  Unchecked: Preventable with logic (NPE, ArithEx, IndexOOB)");
        System.out.println("  Checked: Compiler-enforced handling (FileNotFound, IOException)");
    }
}
