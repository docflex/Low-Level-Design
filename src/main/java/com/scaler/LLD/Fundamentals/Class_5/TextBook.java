package com.scaler.LLD.Fundamentals.Class_5;

/**
 * LLD 5 — Lab: TextBook — Concrete Book (Academic)
 *
 * Milestone B, Task 4.
 */
public class TextBook extends Book {
    private String subject;
    private int edition;
    private BookCategory category;

    public TextBook(String isbn, String title, String author,
                    String subject, int edition, BookCategory category) {
        super(isbn, title, author);
        this.subject = subject;
        this.edition = edition;
        this.category = category;
    }

    // Convenience constructor defaulting to ACADEMIC
    public TextBook(String isbn, String title, String author,
                    String subject, int edition) {
        this(isbn, title, author, subject, edition, BookCategory.ACADEMIC);
    }

    @Override
    void displayBookDetails() {
        System.out.println("[TextBook] " + getTitle());
        System.out.println("  Author:   " + getAuthor());
        System.out.println("  ISBN:     " + getIsbn());
        System.out.println("  Subject:  " + subject);
        System.out.println("  Edition:  " + edition);
        System.out.println("  Category: " + category);
        System.out.println("  Available: " + isAvailable());
    }

    public String getSubject()       { return subject; }
    public int getEdition()          { return edition; }
    public BookCategory getCategory(){ return category; }
}
