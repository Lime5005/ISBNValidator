package com.virtualpairprogrammers.isbntools;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/*
重点：
1， 先写测试，再写代码。根据要测试的数据来重写代码。这样保证覆盖率100%。
2， 比如，我们要测试几个不同的编号，编号的逻辑是什么？怎样才算断言对，或者错？
3， 再根据这个逻辑去写代码。
    1，需要符合逻辑，能被11整除。
    2，需要正好10位数字。
    3，必须是10个数字，非字母。
    4，最后一个数字可以是字母X。
    5，如果最后是X，如何进入逻辑进行最后的计算？ASCII 计算法：X == 10。
    6，有13位数字的也成立？找到逻辑，加入到原有的代码中，让这两种逻辑同时实现，if else

 */
public class ValidateISBNTest {

    @Test
    public void checkAValid10DigitsISBN() {
        ValidateISBN validateISBN = new ValidateISBN();
        boolean result = validateISBN.checkISBN("0140449116");
        assertTrue(result);
        result = validateISBN.checkISBN("0140177396");
        assertTrue(result);
    }

    @Test
    public void checkIfLastLetterXIsValid10DigitsISBN() {
        ValidateISBN validateISBN = new ValidateISBN();
        boolean result = validateISBN.checkISBN("012000030X");
        assertTrue(result);
    }

    @Test
    public void checkAValid13Digits() {
        //978-3127354317
        ValidateISBN validateISBN = new ValidateISBN();
        boolean result = validateISBN.checkISBN("9783127354317");
        assertTrue(result);
        result = validateISBN.checkISBN("9781853267338");
        assertTrue(result);
    }

    @Test
    public void checkAnInValid13Digits() {
        //978-3127354317
        ValidateISBN validateISBN = new ValidateISBN();
        boolean result = validateISBN.checkISBN("9783127354316");
        assertFalse(result);
    }

    @Test
    public void checkAInValid10DigitsISBN() {
        ValidateISBN validateISBN = new ValidateISBN();
        boolean result = validateISBN.checkISBN("0140449117");
        assertFalse(result);
    }

    @Test
    public void checkIfLengthIsTen() {
        ValidateISBN validateISBN = new ValidateISBN();
        // If it's expected error when running the lambda code, then throw it.
        assertThrows(NumberFormatException.class, () -> validateISBN.checkISBN("140449117"));
    }

    @Test
    public void checkIfDigits() {
        ValidateISBN validateISBN = new ValidateISBN();
        assertThrows(NumberFormatException.class, () -> validateISBN.checkISBN("helloworld"));
    }

}
