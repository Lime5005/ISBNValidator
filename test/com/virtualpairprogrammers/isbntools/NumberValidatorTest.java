package com.virtualpairprogrammers.isbntools;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/*
what is prime number?
- A number that is divisible only by itself and 1 (e.g. 2, 3, 5, 7, 11).
 */
public class NumberValidatorTest {

    private NumberValidator validator;

    @BeforeEach
    public void setup() {
        validator = new NumberValidator();
    }

    // A tautology test example: 照搬源代码的逻辑，如果源代码逻辑有问题，根本查不出来。
    /*@Test
    public void checkPrimeNumbers() {
        Integer[] numbers = {1, 15, 23, 35, 60, 63, 78, 207};
        for (Integer number : numbers) {
            boolean isPrime = true;
            int MaxDivisor = (int) Math.sqrt(number);
            for (int counter = 2; counter < MaxDivisor; counter++) {
                if (number % counter == 0) {
                    isPrime = false;
                    break;
                }
            }
            assertEquals(isPrime, validator.isItPrime(number));
        }
    }*/

    @Test
    public void checkIsPrimeNumber() {
        Integer[] numbers = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29};
        for (Integer number : numbers) {
            assertTrue(validator.isItPrime(number));
        }
    }

    @Test
    public void checkIsNotPrimeNumber() {
        Integer[] numbers = {15, 25, 60, 63, 207};
        for (Integer number: numbers) {
            assertFalse(validator.isItPrime(number));
        }
    }

}
