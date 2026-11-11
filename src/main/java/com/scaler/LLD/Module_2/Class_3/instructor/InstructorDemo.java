package com.scaler.LLD.Module_2.Class_3.instructor;

import java.util.function.Supplier;

/** Practice problem: Instructor builder -- valid instructors and each validation rule failing. */
public class InstructorDemo {

    static void tryBuild(String label, Supplier<Instructor> attempt) {
        try {
            System.out.println("  " + label + " -> " + attempt.get());
        } catch (IllegalArgumentException e) {
            System.out.println("  " + label + " -> rejected: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Valid instructors ===");
        tryBuild("all fields", () -> Instructor.builder()
                .name("Rehber").email("rehber@example.com").company("Scaler").salary(2_50_000).age(30).build());
        tryBuild("email and salary skipped", () -> Instructor.builder()
                .name("Riya").company("Acme").age(28).build());

        System.out.println("\n=== Each rule failing ===");
        tryBuild("no name", () -> Instructor.builder()
                .company("Scaler").age(30).build());
        tryBuild("blank name", () -> Instructor.builder()
                .name("   ").company("Scaler").age(30).build());
        tryBuild("age 27", () -> Instructor.builder()
                .name("Aman").company("Scaler").age(27).build());
        tryBuild("not working (no company)", () -> Instructor.builder()
                .name("Kunal").age(35).build());
    }
}
