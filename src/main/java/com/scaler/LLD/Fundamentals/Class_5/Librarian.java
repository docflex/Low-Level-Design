package com.scaler.LLD.Fundamentals.Class_5;

/**
 * LLD 5 — Lab: Librarian — Concrete User
 *
 * Milestone A, Task 5:
 *   - Extends User, overrides abstract methods.
 *   - canBorrowBooks() always returns true (no limit for librarians).
 *   - Has librarian-specific attribute: employeeNumber.
 */
public class Librarian extends User {

    private final String employeeNumber;

    @Override
    void displayDashboard() {
        System.out.println("Librarian Dashboard [" + getUserId() + "]");
        System.out.println("Name: " + this.getName());
        System.out.println("Employee Number: " + this.employeeNumber);
    }

    @Override
    boolean canBorrowBooks() {
        return true;    // Librarians have no borrowing limit
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public Librarian(String employeeNumber, String name, String contactInfo) {
        super(name, contactInfo);
        this.employeeNumber = employeeNumber;
    }
}
