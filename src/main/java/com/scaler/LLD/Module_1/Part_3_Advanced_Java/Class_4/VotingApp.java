package com.scaler.LLD.Module_1.Part_3_Advanced_Java.Class_4;

/**
 * Custom Exceptions demo -- Voting Application.
 * UnderageException extends Exception (checked) -- forces caller to handle.
 * Shows: creating custom exceptions, throw keyword, checked vs unchecked choice.
 */
public class VotingApp {
    public static void main(String[] args) {
        System.out.println("=== Custom Exception: Voting App ===\n");

        // Test 1: Eligible voter (age >= 18)
        System.out.println("--- Checking voter age 25 ---");
        try {
            boolean eligible = doAgeCheck(25);
            System.out.println("  Eligible to vote: " + eligible);
        } catch (UnderageException e) {
            System.out.println("  Rejected: " + e.getMessage());
        }

        // Test 2: Underage voter (age < 18)
        System.out.println("\n--- Checking voter age 15 ---");
        try {
            boolean eligible = doAgeCheck(15);
            System.out.println("  Eligible to vote: " + eligible);
        } catch (UnderageException e) {
            System.out.println("  Rejected: " + e.getMessage());
        }

        // Test 3: Edge case (exactly 18)
        System.out.println("\n--- Checking voter age 18 ---");
        try {
            boolean eligible = doAgeCheck(18);
            System.out.println("  Eligible to vote: " + eligible);
        } catch (UnderageException e) {
            System.out.println("  Rejected: " + e.getMessage());
        }

        System.out.println("\n=== Key takeaway ===");
        System.out.println("  extends Exception   -> checked (caller MUST handle or declare)");
        System.out.println("  extends RuntimeException -> unchecked (no forced handling)");
        System.out.println("  Use checked when caller can recover (retry, re-enter input)");
        System.out.println("  Use unchecked when no recovery possible (underage = quit)");
    }

    /**
     * Checks if voter is eligible (age >= 18).
     * @throws UnderageException checked -- forces caller to handle or propagate
     */
    public static boolean doAgeCheck(int age) throws UnderageException {
        if (age < 18) {
            throw new UnderageException("Voter cannot vote as they are below 18 (age=" + age + ")");
        }
        return true;
    }
}
