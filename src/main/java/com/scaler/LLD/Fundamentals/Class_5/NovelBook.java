package com.scaler.LLD.Fundamentals.Class_5;

/**
 * LLD 5 — Lab: NovelBook — Concrete Book (Fiction/Non-Fiction)
 *
 * Milestone B, Task 5.
 */
public class NovelBook extends Book {
    private String genre;
    private BookCategory category;

    public NovelBook(String isbn, String title, String author,
                     String genre, BookCategory category) {
        super(isbn, title, author);
        this.genre = genre;
        this.category = category;
    }

    // Convenience constructor defaulting to FICTION
    public NovelBook(String isbn, String title, String author, String genre) {
        this(isbn, title, author, genre, BookCategory.FICTION);
    }

    @Override
    void displayBookDetails() {
        System.out.println("[NovelBook] " + getTitle());
        System.out.println("  Author:   " + getAuthor());
        System.out.println("  ISBN:     " + getIsbn());
        System.out.println("  Genre:    " + genre);
        System.out.println("  Category: " + category);
        System.out.println("  Available: " + isAvailable());
    }

    public String getGenre()         { return genre; }
    public BookCategory getCategory(){ return category; }
}
