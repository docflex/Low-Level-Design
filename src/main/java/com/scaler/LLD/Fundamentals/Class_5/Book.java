package com.scaler.LLD.Fundamentals.Class_5;

/**
 * LLD 5 — Lab: Book — Abstract Class implementing Lendable
 *
 * Milestone B, Tasks 2–3:
 *   - Abstract AND implements an interface: both concepts working together.
 *   - Lendable methods (lend, returnItem, isAvailable) have concrete
 *     implementations here because lending logic is the same for all books.
 *   - displayBookDetails() is abstract because each book type (TextBook,
 *     NovelBook) has different fields to display.
 *
 * FIX APPLIED — Constructor Chaining:
 *   Original duplicated `this.isAvailable = true` in all 3 constructors.
 *   Now all constructors chain to Book(String, String, String).
 *
 * FIX APPLIED — Missing toString():
 *   Added for readable search results and debugging.
 */
public abstract class Book implements Lendable {
    private String isbn;
    private String title;
    private String author;
    private boolean isAvailable;

    @Override
    public boolean lend(User user) {
        if (this.isAvailable && user.canBorrowBooks()) {
            this.isAvailable = false;
            return true;
        }
        return false;
    }

    @Override
    public void returnItem(User user) {
        this.isAvailable = true;
    }

    @Override
    public boolean isAvailable() {
        return this.isAvailable;
    }

    abstract void displayBookDetails();

    // CONSTRUCTORS — chained to the parameterized constructor (base)

    // BASE — the ONLY place that sets isAvailable
    public Book(String isbn, String title, String author) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.isAvailable = true;
    }

    public Book()            { this(null, null, null); }
    public Book(Book other)  { this(other.isbn, other.title, other.author); }

    // GETTERS

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{isbn='" + isbn
                + "', title='" + title
                + "', author='" + author
                + "', available=" + isAvailable + "}";
    }
}
