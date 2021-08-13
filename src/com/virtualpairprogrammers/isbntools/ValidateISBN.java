package com.virtualpairprogrammers.isbntools;

public class ValidateISBN {

    public boolean checkISBN(String isbn) {

        if (isbn.length() == 13) {
            int total = 0;
            for (int i = 0; i < 13; i++) {
                if (i % 2 == 0) {
                    total += Character.getNumericValue(isbn.charAt(i));
                } else {
                    total += Character.getNumericValue(isbn.charAt(i)) * 3;
                }
            }
            return total % 10 == 0;
        } else {
            if (isbn.length() != 10) throw new NumberFormatException("ISBN should be 10 digits long!");

            int total = 0;
            for (int i = 0; i < 10; i++) {
                if (!Character.isDigit(isbn.charAt(i))) {
                    if (i == 9 && isbn.charAt(i) == 'X') {
                        total += 10; // It will continue to check.
                    } else {
                        throw new NumberFormatException("ISBN should be only digits!");
                    }
                } else {
                    total += Character.getNumericValue(isbn.charAt(i)) * (10 - i);
                }
            }
            return total % 11 == 0;
        }
    }
}
