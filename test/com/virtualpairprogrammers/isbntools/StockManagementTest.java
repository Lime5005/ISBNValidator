package com.virtualpairprogrammers.isbntools;

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
 */
public class StockManagementTest {

    /*@Test
    public void testGetACorrectLocatorCode() {
        // 1, Mockup a service with what it should return:
        ExternalISBNDataService testWebService = new ExternalISBNDataService() {
            @Override
            public ABook lookup(String isbn) {
                 return new ABook(isbn, "John Steinbeck", "Of Mice and Men");
            }
        };

        ExternalISBNDataService testDbService = new ExternalISBNDataService() {
            @Override
            public ABook lookup(String isbn) {
                return null;
            }
        };

        // 2, Bring the mocked service to my class:
        StockManager stockManager = new StockManager();
        stockManager.setWebService(testWebService);
        stockManager.setDatabaseService(testDbService);
        // 3, Test it with the method I designed:
        String isbn = "0140177396";
        String locatorCode = stockManager.getLocatorCode(isbn);
        assertEquals("7396J4",  locatorCode);
    }*/

    // Using stub to mock the Object -> when().thenReturn() -> assertEquals()
    @Test
    public void testGetACorrectLocatorCodeUsingMockObject() {
        ExternalISBNDataService webService = mock(ExternalISBNDataService.class);
        ExternalISBNDataService dbService = mock(ExternalISBNDataService.class);
        String isbn = "0140177396";
        StockManager stockManager = new StockManager();
        stockManager.setWebService(webService);
        stockManager.setDatabaseService(dbService);
        when(dbService.lookup(anyString())).thenReturn(null);
        when(webService.lookup(anyString())).thenReturn(new ABook(isbn, "John Steinbeck", "Of Mice and Men"));
        String locatorCode = stockManager.getLocatorCode(isbn);
        assertEquals("7396J4",  locatorCode);
    }

    @Test
    public void dbServiceIsUsedIfDataPresent() {
        // 1, Use Mockito to mock a dummy class which will return null for any data:
        ExternalISBNDataService webService = mock(ExternalISBNDataService.class);
        ExternalISBNDataService dbService = mock(ExternalISBNDataService.class);

        // 2, Use When, thenReturn to mock the interface's result:
        when(dbService.lookup("0140177396"))
                .thenReturn(new ABook("0140177396", "Some author", "Some title"));

        StockManager stockManager = new StockManager();
        stockManager.setWebService(webService);
        stockManager.setDatabaseService(dbService);
        // We don't care if it's right or wrong, just want to know if it's been called.
        String isbn = "0140177396";
        String locatorCode = stockManager.getLocatorCode(isbn);
        //assertEquals("7396J4",  locatorCode); // org.opentest4j.AssertionFailedError:Expected:7396J4 Actual:7396S2

        // 3, Does myMethod which belongs to myInterface have been called once with the params I asked?
        verify(dbService).lookup(isbn);
        // 4, Does myOtherMethod haven't been called as expected?
        verify(webService, never()).lookup(anyString());
    }

    @Test
    public void webServiceIsUsedIfDataNotPresent() {
        ExternalISBNDataService webService = mock(ExternalISBNDataService.class);
        ExternalISBNDataService dbService = mock(ExternalISBNDataService.class);

        // 2, Use When, thenReturn to mock the interface's result:
        when(dbService.lookup("0140177396")).thenReturn(null);
        when(webService.lookup("0140177396"))
                .thenReturn(new ABook("0140177396", "Some author", "Some title"));

        StockManager stockManager = new StockManager();
        stockManager.setWebService(webService);
        stockManager.setDatabaseService(dbService);

        String isbn = "0140177396";
        String locatorCode = stockManager.getLocatorCode(isbn);

        // 3, Does myMethod which belongs to myInterface have been called once with the params I asked?
        verify(dbService).lookup(isbn);
        verify(webService).lookup(isbn);
    }

}
