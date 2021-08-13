package com.virtualpairprogrammers.isbntools;

public class ValidateISBN {

    public static final int LONG_LENGTH_ISBN = 13;
    public static final int SHORT_LENGTH_ISBN = 10;
    public static final int SHORT_ISBN_MULTIPLIER = 11;
    public static final int LONG_ISBN_MULTIPLIER = 10;

    public boolean checkISBN(String isbn) {

        if (isbn.length() == LONG_LENGTH_ISBN) {
            return isValid13DigitsISBN(isbn);
        } else {
            if (isbn.length() != SHORT_LENGTH_ISBN) throw new NumberFormatException("ISBN should be 10 digits long!");
            return isValid10DigitsISBN(isbn);
        }
    }

    private boolean isValid10DigitsISBN(String isbn) {
        int total = 0;
        for (int i = 0; i < SHORT_LENGTH_ISBN; i++) {
            if (!Character.isDigit(isbn.charAt(i))) {
                if (i == 9 && isbn.charAt(i) == 'X') {
                    total += 10; // It will continue to check.
                } else {
                    throw new NumberFormatException("ISBN should be only digits!");
                }
            } else {
                total += Character.getNumericValue(isbn.charAt(i)) * (SHORT_LENGTH_ISBN - i);
            }
        }
        return total % SHORT_ISBN_MULTIPLIER == 0;
    }

    private boolean isValid13DigitsISBN(String isbn) {
        int total = 0;
        for (int i = 0; i < LONG_LENGTH_ISBN; i++) {
            if (i % 2 == 0) {
                total += Character.getNumericValue(isbn.charAt(i));
            } else {
                total += Character.getNumericValue(isbn.charAt(i)) * 3;
            }
        }
        return total % LONG_ISBN_MULTIPLIER == 0;
    }
}
