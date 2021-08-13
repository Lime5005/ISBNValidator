package com.virtualpairprogrammers.isbntools;

public class ABook {
    private String isbn;
    private String author;
    private String title;

    public ABook(String isbn, String author, String title) {
        this.isbn = isbn;
        this.author = author;
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }
}
