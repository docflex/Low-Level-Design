package com.scaler.LLD.Fundamentals.Class_5;

import java.util.List;

/**
 * LLD 5 — Lab: TestLMS — Milestone C Comprehensive Demo
 *
 * Demonstrates ALL milestones working together:
 *   A: Users (abstract class, static counter, constructor chaining)
 *   B: Books + Lendable (interface, abstract Book, concrete TextBook/NovelBook)
 *   C: LibraryManagementSystem (collections, lending, returning, search)
 *
 * Run: Right-click → Run 'TestLMS.main()'
 */
public class TestLMS {

    public static void main(String[] args) {

        LibraryManagementSystem lms = new LibraryManagementSystem();

        // ══════════════════════════════════════════════════════════════
        //  STEP 1: Create and Register Users
        // ══════════════════════════════════════════════════════════════

        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.println("║  STEP 1: Register Users                        ║");
        System.out.println("╚══════════════════════════════════════════════════╝");

        Member m1 = new Member("Rehber", "9876543210");
        Member m2 = new Member("Rahul", "1234567890");
        Member m3 = new Member(m2);     // copy constructor — gets fresh ID
        m3.setName("Rahul-Copy");

        Librarian lib = new Librarian("EMP-001", "Rohit", "5551234567");

        lms.registerUser(m1);
        lms.registerUser(m2);
        lms.registerUser(m3);
        lms.registerUser(lib);

        System.out.println("Registered " + User.getTotalUsers() + " users:");
        System.out.println("  " + m1);
        System.out.println("  " + m2);
        System.out.println("  " + m3 + "  (copy of m2, different ID)");
        System.out.println("  " + lib);


        // ══════════════════════════════════════════════════════════════
        //  STEP 2: Add Books to Inventory
        // ══════════════════════════════════════════════════════════════

        System.out.println("\n╔══════════════════════════════════════════════════╗");
        System.out.println("║  STEP 2: Add Books to Inventory                ║");
        System.out.println("╚══════════════════════════════════════════════════╝");

        TextBook tb1 = new TextBook("978-1449373320", "DDIA",
                "Kleppmann", "Systems Design", 1, BookCategory.ACADEMIC);
        TextBook tb2 = new TextBook("978-0134685991", "Effective Java",
                "Joshua Bloch", "Java", 3, BookCategory.ACADEMIC);
        TextBook tb3 = new TextBook("978-0201633610", "Design Patterns",
                "Gang of Four", "Software Engineering", 1);

        NovelBook nb1 = new NovelBook("978-0061120084", "To Kill a Mockingbird",
                "Harper Lee", "Classic", BookCategory.FICTION);
        NovelBook nb2 = new NovelBook("978-0451524935", "1984",
                "George Orwell", "Dystopian", BookCategory.FICTION);
        NovelBook nb3 = new NovelBook("978-0743273565", "The Great Gatsby",
                "F. Scott Fitzgerald", "Classic");

        lms.addBook(tb1);
        lms.addBook(tb2);
        lms.addBook(tb3);
        lms.addBook(nb1);
        lms.addBook(nb2);
        lms.addBook(nb3);

        System.out.println("Added " + lms.getBookInventory().size() + " books to inventory.");
        lms.displayAllBooks();


        // ══════════════════════════════════════════════════════════════
        //  STEP 3: Display User Dashboards
        // ══════════════════════════════════════════════════════════════

        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.println("║  STEP 3: User Dashboards                       ║");
        System.out.println("╚══════════════════════════════════════════════════╝");

        lms.displayRegisteredUsers();


        // ══════════════════════════════════════════════════════════════
        //  STEP 4: Lending Books
        // ══════════════════════════════════════════════════════════════

        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.println("║  STEP 4: Lending Books                         ║");
        System.out.println("╚══════════════════════════════════════════════════╝");

        // 4a. Lend DDIA to Rehber
        System.out.println("Lend '" + tb1.getTitle() + "' to " + m1.getName() + ":");
        System.out.println("  Success? " + lms.lendBook(m1, tb1));
        System.out.println("  Book available? " + tb1.isAvailable());
        System.out.println("  " + m1.getName() + " borrowed count: " + m1.getBorrowedBooksCount());

        // 4b. Try to lend the same book to Rahul (should fail — not available)
        System.out.println("\nLend '" + tb1.getTitle() + "' to " + m2.getName() + " (already lent):");
        System.out.println("  Success? " + lms.lendBook(m2, tb1));

        // 4c. Lend a different book to Rahul
        System.out.println("\nLend '" + nb1.getTitle() + "' to " + m2.getName() + ":");
        System.out.println("  Success? " + lms.lendBook(m2, nb1));


        // ══════════════════════════════════════════════════════════════
        //  STEP 5: Return Books
        // ══════════════════════════════════════════════════════════════

        System.out.println("\n╔══════════════════════════════════════════════════╗");
        System.out.println("║  STEP 5: Returning Books                       ║");
        System.out.println("╚══════════════════════════════════════════════════╝");

        System.out.println("Return '" + tb1.getTitle() + "' from " + m1.getName() + ":");
        lms.returnBook(m1, tb1);
        System.out.println("  Book available? " + tb1.isAvailable());
        System.out.println("  " + m1.getName() + " borrowed count: " + m1.getBorrowedBooksCount());

        // Now Rahul can borrow the returned book
        System.out.println("\nLend '" + tb1.getTitle() + "' to " + m2.getName() + " (after return):");
        System.out.println("  Success? " + lms.lendBook(m2, tb1));


        // ══════════════════════════════════════════════════════════════
        //  STEP 6: Borrowing Limit
        // ══════════════════════════════════════════════════════════════

        System.out.println("\n╔══════════════════════════════════════════════════╗");
        System.out.println("║  STEP 6: Borrowing Limit (Max 5)               ║");
        System.out.println("╚══════════════════════════════════════════════════╝");

        // m2 already has 2 books (Mockingbird + DDIA). Lend 3 more to hit the limit.
        System.out.println(m2.getName() + " currently has " + m2.getBorrowedBooksCount() + " books.");
        System.out.println("Lending 3 more to hit the limit of 5...");
        lms.lendBook(m2, tb2);
        lms.lendBook(m2, nb2);
        lms.lendBook(m2, tb3);
        System.out.println(m2.getName() + " now has " + m2.getBorrowedBooksCount() + " books.");
        System.out.println("Can borrow more? " + m2.canBorrowBooks());

        // Try to borrow a 6th — should fail
        System.out.println("\nAttempt to borrow 6th book ('" + nb3.getTitle() + "'):");
        System.out.println("  Success? " + lms.lendBook(m2, nb3));
        System.out.println("  " + nb3.getTitle() + " still available? " + nb3.isAvailable());


        // ══════════════════════════════════════════════════════════════
        //  STEP 7: Search Books (Method Overloading)
        // ══════════════════════════════════════════════════════════════

        System.out.println("\n╔══════════════════════════════════════════════════╗");
        System.out.println("║  STEP 7: Search Books (Overloaded Methods)     ║");
        System.out.println("╚══════════════════════════════════════════════════╝");

        // 7a. Search by title or author
        System.out.println("searchBooks(\"java\"):");
        List<Book> javaBooks = lms.searchBooks("java");
        for (Book b : javaBooks) {
            System.out.println("  " + b);
        }

        System.out.println("\nsearchBooks(\"Orwell\"):");
        List<Book> orwellBooks = lms.searchBooks("Orwell");
        for (Book b : orwellBooks) {
            System.out.println("  " + b);
        }

        // 7b. Search by criteria AND type (overloaded method)
        System.out.println("\nsearchBooks(\"design\", \"TextBook\"):");
        List<Book> designTextbooks = lms.searchBooks("design", "TextBook");
        for (Book b : designTextbooks) {
            System.out.println("  " + b);
        }

        System.out.println("\nsearchBooks(\"classic\", \"NovelBook\")  (no match — genre not searched):");
        List<Book> classicNovels = lms.searchBooks("classic", "NovelBook");
        System.out.println("  Results: " + classicNovels.size());

        // 7c. Search that returns no results
        System.out.println("\nsearchBooks(\"quantum physics\"):");
        List<Book> noResults = lms.searchBooks("quantum physics");
        System.out.println("  Results: " + noResults.size());


        // ══════════════════════════════════════════════════════════════
        //  STEP 8: Final State
        // ══════════════════════════════════════════════════════════════

        System.out.println("\n╔══════════════════════════════════════════════════╗");
        System.out.println("║  STEP 8: Final System State                    ║");
        System.out.println("╚══════════════════════════════════════════════════╝");

        System.out.println("Total users in system: " + User.getTotalUsers());
        System.out.println("Books in inventory: " + lms.getBookInventory().size());
        System.out.println();

        lms.displayAllBooks();

        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.println("║  ALL MILESTONES COMPLETE                       ║");
        System.out.println("╚══════════════════════════════════════════════════╝");
    }
}
