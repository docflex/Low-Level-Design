package com.scaler.LLD.Module_1.Part_3_Advanced_Java.Class_4;

public class Book extends Item {
    private final String author;

    public Book(String id, String name, double price, int quantity, String author) {
        super(id, name, price, quantity);
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }
}
