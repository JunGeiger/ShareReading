package com.TheWorldFirst.ShareReading.util;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

public class CrawlerUtil {

    //浏览器位置
    private static final String browserFireFox = "D:\\FireFox\\firefox.exe";

    //geckodriver位置
    private static final String geckodriver = "src/main/resources/static/util/geckodriver.exe";

    //等待时间
    private static int waitTime = 3;

    /**
     *获取火狐浏览器
     * @return
     */
    private static FirefoxDriver getFirefoxBinary() {
        File pathToBinary = new File(browserFireFox);
        FirefoxBinary firefoxBinary = new FirefoxBinary(pathToBinary);
        firefoxBinary.addCommandLineOptions("--headless");
        System.setProperty("webdriver.gecko.driver", geckodriver);
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.setBinary(firefoxBinary);
        FirefoxDriver firefoxDriver = new FirefoxDriver(firefoxOptions);
        return firefoxDriver;
    }

    /**
     *根据isbn查询书籍页面(根据火狐)
     * @param isbn
     * @return
     */
    public static String getBookUrlByFirefox(String isbn) {
        FirefoxDriver driver = getFirefoxBinary();
        driver.get("https://search.douban.com/book/subject_search?search_text=" + isbn + "&cat=1001");
        WebDriverWait wait = new WebDriverWait(driver, 3);
        WebElement firstResult = wait.until(presenceOfElementLocated(By.className("cover-link")));
        String bookUrl = firstResult.getAttribute("href");
        return bookUrl;
    }

    /**
     *根据豆瓣url查询书籍信息(根据火狐)
     * @param url
     * @return
     */
    public static String getBookInfoByFirefox(String url) {
        FirefoxDriver driver = getFirefoxBinary();
        driver.get(url);
        WebDriverWait wait = new WebDriverWait(driver, waitTime);
        wait.until(presenceOfElementLocated(By.className("article")));
        return driver.getPageSource();
    }
}
