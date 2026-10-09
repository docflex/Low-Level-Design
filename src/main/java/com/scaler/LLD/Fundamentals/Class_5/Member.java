package com.scaler.LLD.Fundamentals.Class_5;

/**
 * LLD 5 — Lab: Member — Concrete User (Student/Professor)
 *
 * Milestone A, Task 4:
 *   - Extends User, overrides abstract methods.
 *   - Has a borrowing limit enforced by canBorrowBooks().
 *   - incrementBorrowCount() / decrementBorrowCount() are called by
 *     LibraryManagementSystem when lending/returning books (Milestone C).
 */
public class Member extends User {
    private int borrowedBooksCount;
    private static final int MAX_BORROW_LIMIT = 5;

    @Override
    void displayDashboard() {
        System.out.println("Member Dashboard [" + getUserId() + "]");
        System.out.println("Name: " + this.getName());
        System.out.println("Books Borrowed: " + this.borrowedBooksCount + "/" + MAX_BORROW_LIMIT);
    }

    @Override
    boolean canBorrowBooks() {
        return this.borrowedBooksCount < MAX_BORROW_LIMIT;
    }

    // Called by LibraryManagementSystem.lendBook()
    public void incrementBorrowCount() {
        this.borrowedBooksCount++;
    }

    // Called by LibraryManagementSystem.returnBook()
    public void decrementBorrowCount() {
        if (this.borrowedBooksCount > 0) {
            this.borrowedBooksCount--;
        }
    }

    public int getBorrowedBooksCount() {
        return borrowedBooksCount;
    }

    // CONSTRUCTORS

    public Member(String name, String contactInfo) {
        super(name, contactInfo);
        this.borrowedBooksCount = 0;
    }

    public Member()              { this(null, null); }
    public Member(Member other)  { super(other); this.borrowedBooksCount = other.borrowedBooksCount; }
}
