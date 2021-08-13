package com.virtualpairprogrammers.isbntools;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/*
Test result:
如何获得LocatorCode?
组成：根据ISBN末尾4位数字 + 姓名首字母 + 书名字数。
    1，得到ISBN。
    2，创建Class，里面加Get方法。
    3，已知：姓名首字母和书名字数必须依赖于第三方API获得。
    4，自建一个interface模拟第三方，由它根据一个ISBN就能拿到书的信息，包括作者名，书名。
    5，创建我们要的Book class, 包括author, title.
    6, 注意：java.awt.print.Book != Book, 要避免与已经存在的 class 重名。
    7，在测试的时候去实现 interface, 手动覆盖以便测试。
        1，在方法上引入interface（setService），用 interface 带来的数据 book 去加作者名与书名。
        2，测试的时候手动实现 interface 会带来的数据。
        3，这种测试的局限性，只能测试一个数据。
Test behavior, not result:
    1, We want to get ABook from a database first, if we cannot get ABook from the database,
    then go to web service to search for it.
    2, Add a database service interface, just beside the web service.
    3, Test if the database has been used if there is a data.
    4, Test if the webService has been used if no data.
    5, Using stub so no need to override the method in myInterface.
Refactor again:
    JUnit Setup() and TearDown() == Constructor and deConstructor
    1, Declare as class lever variables.
    2, Initiate in setup() with @BeforeEach, so each test can still run individually.
Avoid Tautology:
    Tautology means using the same logic in the test as in the implementation.
    1, See example: NumberValidator.java
    2, A test should not involve any logic, it has nothing to calculate.
    3, Since the test is to check "is or not" logic, divide into 2 tests.
    4, One for check "is", the other for check "not".

 */
public class StockManagementTest {

    ExternalISBNDataService webService;
    ExternalISBNDataService dbService;
    StockManager stockManager;

    @BeforeEach
    public void setup() {
        System.out.println("Setting up...");
        webService = mock(ExternalISBNDataService.class);
        dbService = mock(ExternalISBNDataService.class);
        stockManager = new StockManager();
        stockManager.setWebService(webService);
        stockManager.setDatabaseService(dbService);
    }

    // Using stub to mock the Object -> when().thenReturn() -> assertEquals()
    @Test
    public void testGetACorrectLocatorCodeUsingMockObject() {
        String isbn = "0140177396";

        when(dbService.lookup(anyString())).thenReturn(null);
        when(webService.lookup(anyString()))
                .thenReturn(new ABook(isbn, "John Steinbeck", "Of Mice and Men"));

        String locatorCode = stockManager.getLocatorCode(isbn);
        assertEquals("7396J4",  locatorCode);
    }

    @Test
    public void dbServiceIsUsedIfDataPresent() {
        when(dbService.lookup("0140177396"))
                .thenReturn(new ABook("0140177396", "Some author", "Some title"));

        String isbn = "0140177396";
        String locatorCode = stockManager.getLocatorCode(isbn);
        verify(dbService).lookup(isbn);
        verify(webService, never()).lookup(anyString());
    }

    @Test
    public void webServiceIsUsedIfDataNotPresent() {
        when(dbService.lookup("0140177396")).thenReturn(null);
        when(webService.lookup("0140177396"))
                .thenReturn(new ABook("0140177396", "Some author", "Some title"));

        String isbn = "0140177396";
        String locatorCode = stockManager.getLocatorCode(isbn);
        verify(dbService).lookup(isbn);
        verify(webService).lookup(isbn);
    }

}
