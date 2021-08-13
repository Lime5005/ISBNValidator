package com.virtualpairprogrammers.isbntools;

public class StockManager {

    private ExternalISBNDataService service;

    public void setService(ExternalISBNDataService service) {
        this.service = service;
    }

    public String getLocatorCode(String isbn) {
        ABook book = service.lookup(isbn);

        StringBuilder builder = new StringBuilder(); // Start to build a String with "";
        builder.append(isbn.substring(isbn.length() - 4));
        builder.append(book.getAuthor().charAt(0));
        builder.append(book.getTitle().split(" ").length); // length or length() depends.

        return builder.toString();
        // StringBuilder.append() returns a StringBuilder Object, Object.toString() returns a String.
    }
}
