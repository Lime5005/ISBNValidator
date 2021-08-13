package com.virtualpairprogrammers.isbntools;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/*
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
 */
public class StockManagementTest {

    @Test
    public void testGetACorrectLocatorCode() {
        // 1, Mockup a service with what it should return:
        ExternalISBNDataService testService = new ExternalISBNDataService() {
            @Override
            public ABook lookup(String isbn) {
                 return new ABook(isbn, "John Steinbeck", "Of Mice and Men");
            }
        };

        // 2, Bring the mocked service to my class:
        StockManager stockManager = new StockManager();
        stockManager.setService(testService);

        // 3, Test it with the method I designed:
        String isbn = "0140177396";
        String locatorCode = stockManager.getLocatorCode(isbn);
        assertEquals("7396J4",  locatorCode);
    }
}
