package com.scaler.LLD.Fundamentals.Class_5;

import java.util.ArrayList;
import java.util.List;

/**
 * LLD 5 — Lab: LibraryManagementSystem — Milestone C
 *
 * This class ties everything together:
 *   - Manages collections of books and users
 *   - Coordinates lending/returning (updates both Book and Member state)
 *   - Provides search functionality (method overloading = compile-time polymorphism)
 *   - Displays inventory and user dashboards
 *
 * KEY DESIGN DECISIONS:
 *   - lendBook() delegates to book.lend(member) AND calls member.incrementBorrowCount().
 *     Neither Book nor Member does both — the LMS coordinates them.
 *   - searchBooks() is overloaded: one version searches by title/author,
 *     the other also filters by book type (TextBook or NovelBook).
 */
public class LibraryManagementSystem {

    private final List<User> registeredUsers = new ArrayList<>();
    private final List<Book> bookInventory = new ArrayList<>();

    // ── Registration & Inventory ──

    public void registerUser(User user) {
        registeredUsers.add(user);
    }

    public void addBook(Book book) {
        bookInventory.add(book);
    }

    // ── Lending ──

    /**
     * Lend a book to a member.
     *
     * Coordination: book.lend() marks the book unavailable,
     * then we increment the member's borrow count.
     * Both must happen together — this is why the LMS coordinates.
     *
     * @return true if lending was successful
     */
    public boolean lendBook(Member member, Book book) {
        boolean success = book.lend(member);
        if (success) {
            member.incrementBorrowCount();
        }
        return success;
    }

    /**
     * Return a book from a member.
     *
     * Coordination: book.returnItem() marks the book available,
     * then we decrement the member's borrow count.
     */
    public void returnBook(Member member, Book book) {
        book.returnItem(member);
        member.decrementBorrowCount();
    }

    // ── Search (Method Overloading = Compile-Time Polymorphism) ──

    /**
     * Search books by title or author (case-insensitive substring match).
     */
    public List<Book> searchBooks(String criteria) {
        List<Book> results = new ArrayList<>();
        String lowerCriteria = criteria.toLowerCase();
        for (Book book : bookInventory) {
            if ((book.getTitle() != null && book.getTitle().toLowerCase().contains(lowerCriteria))
                    || (book.getAuthor() != null && book.getAuthor().toLowerCase().contains(lowerCriteria))) {
                results.add(book);
            }
        }
        return results;
    }

    /**
     * Overloaded: search by criteria AND filter by book type.
     *
     * @param type "TextBook" or "NovelBook"
     */
    public List<Book> searchBooks(String criteria, String type) {
        List<Book> results = new ArrayList<>();
        String lowerCriteria = criteria.toLowerCase();
        for (Book book : bookInventory) {
            if (!book.getClass().getSimpleName().equalsIgnoreCase(type)) {
                continue;
            }
            if ((book.getTitle() != null && book.getTitle().toLowerCase().contains(lowerCriteria))
                    || (book.getAuthor() != null && book.getAuthor().toLowerCase().contains(lowerCriteria))) {
                results.add(book);
            }
        }
        return results;
    }

    // ── Display ──

    public void displayAllBooks() {
        System.out.println("--- Book Inventory (" + bookInventory.size() + " books) ---");
        for (Book book : bookInventory) {
            book.displayBookDetails();
            System.out.println();
        }
    }

    public void displayRegisteredUsers() {
        System.out.println("--- Registered Users (" + registeredUsers.size() + " users) ---");
        for (User user : registeredUsers) {
            user.displayDashboard();
            System.out.println();
        }
    }

    // ── Getters for testing ──

    public List<Book> getBookInventory()     { return bookInventory; }
    public List<User> getRegisteredUsers()   { return registeredUsers; }
}
