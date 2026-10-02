package com.scaler.LLD.Fundamentals.Class_1;

/**
 * LLD 1 — Intro to LLD: The Student Entity (OOP Style)
 *
 * This is the OOP counterpart to the procedural arrays in ProceduralVsOOP.java.
 *
 * KEY CONCEPTS DEMONSTRATED:
 *   - Class as a blueprint (attributes + behaviors)
 *   - Private fields (data ownership — no one outside can directly corrupt)
 *   - Validation inside the constructor (the object protects its own invariants)
 *   - Meaningful toString() for debugging
 *   - Getters/setters as controlled access points
 *
 * ENTITY IDENTIFICATION (the "find the nouns" method):
 *   When designing a system (Scaler, Uber, Netflix), ask:
 *     "What are the NOUNS in this domain?"
 *   Each noun is a candidate class. This is the same method used
 *   in Schema Design (SQL 11-12) to find tables.
 *
 * Run: This class is used by ProceduralVsOOP.main() — run that instead.
 */
public class Student {

    // ── Attributes (private — data ownership) ──
    private String name;
    private String email;
    private int gradYear;
    private String currentCompany;

    // ── Constructor (with validation) ──
    public Student(String name, String email, int gradYear, String currentCompany) {
        this.name = name;
        this.email = email;
        this.currentCompany = currentCompany;

        // Validation: the OBJECT protects its own invariants.
        // In procedural style, every function that touches gradYear
        // would need to duplicate this check.
        if (gradYear < 1950 || gradYear > 2035) {
            System.out.println("    [WARN] Invalid gradYear " + gradYear + " — defaulting to 2024.");
            this.gradYear = 2024;
        } else {
            this.gradYear = gradYear;
        }
    }

    // ── Behaviors ──

    public void joinClass(String className) {
        System.out.println("  " + name + " joined class: " + className);
    }

    public void solveAssignment(String assignmentName) {
        System.out.println("  " + name + " solved: " + assignmentName);
    }

    public void giveMockInterview() {
        System.out.println("  " + name + " completed a mock interview.");
    }

    // ── Getters & Setters (controlled access) ──

    public String getName() { return name; }

    public String getEmail() { return email; }

    public void setEmail(String email) {
        // Could add email format validation here
        this.email = email;
    }

    public int getGradYear() { return gradYear; }

    public String getCurrentCompany() { return currentCompany; }

    public void setCurrentCompany(String currentCompany) {
        this.currentCompany = currentCompany;
    }

    // ── toString (meaningful output for debugging) ──
    @Override
    public String toString() {
        return "Student{name='" + name + "', email='" + email
                + "', gradYear=" + gradYear + ", company='" + currentCompany + "'}";
    }
}
