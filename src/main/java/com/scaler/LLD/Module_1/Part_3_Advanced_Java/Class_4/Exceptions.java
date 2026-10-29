package com.scaler.LLD.Module_1.Part_3_Advanced_Java.Class_4;

import java.io.FileNotFoundException;

/**
 * Try-Catch-Finally ordering demo.
 * Shows: catch type matching, child-before-parent ordering,
 * finally always executes, and unmatched exceptions.
 */
public class Exceptions {
    public static void main(String[] args) {

        // --- Example 1: Checked exception caught by matching catch ---
        System.out.println("=== Example 1: Matching catch (FileNotFoundException) ===");
        try {
            int x = 10;
            System.out.println("  B");
            if (x % 2 != 0) {
                System.out.println("  C");
                throw new RuntimeException("odd path");
            } else {
                throw new FileNotFoundException("even path");
            }
        } catch (FileNotFoundException e) {
            System.out.println("  A (FileNotFoundException caught)");
        } catch (Exception e) {
            // Parent catch MUST come after child catch (else unreachable compile error)
            System.out.println("  D (Exception caught: " + e.getMessage() + ")");
        } finally {
            System.out.println("  E (finally -- always runs)");
        }
        // Output: B, C, D, E  (x=10 is even? No, 10%2==0 so even path? 10%2=0, so not !=0)
        // Actually 10 % 2 == 0, so the else branch runs: throws FNFE -> caught by FNFE catch
        // Wait, 10 % 2 = 0, so x % 2 != 0 is false -> else branch -> FNFE -> caught
        // Output: B, A, E

        // --- Example 2: RuntimeException with non-matching catch + finally ---
        System.out.println("\n=== Example 2: Non-matching catch + finally ===");
        try {
            try {
                int x = 10;
                System.out.println("  B");
                if (x % 2 != 0) {
                    System.out.println("  C");
                    throw new RuntimeException("odd path");
                } else {
                    throw new ArrayIndexOutOfBoundsException("even path");
                }
            } catch (ArithmeticException e) {
                // This does NOT match AIOOB -- exception propagates
                System.out.println("  A (ArithmeticException caught)");
            } finally {
                // Finally STILL runs even when exception is not caught
                System.out.println("  E (finally -- always runs)");
            }
        } catch (Exception e) {
            // Outer catch handles the uncaught AIOOB
            System.out.println("  Outer catch: " + e.getClass().getSimpleName()
                    + " -- " + e.getMessage());
        }
        // Output: B, E, Outer catch: ArrayIndexOutOfBoundsException

        // --- Example 3: finally and synchronized unlock analogy ---
        System.out.println("\n=== Key takeaway ===");
        System.out.println("  finally block = guaranteed cleanup (like ATM card ejection)");
        System.out.println("  synchronized uses Lock.unlock() in its own finally behind the scenes");
    }
}
