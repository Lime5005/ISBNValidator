package com.virtualpairprogrammers.isbntools;

public class NumberValidator {

    public boolean isItPrime(int number) {
        int MaxDivisor = (int)Math.sqrt(number);
        for (int i = 2; i <= MaxDivisor; i ++) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }
}
