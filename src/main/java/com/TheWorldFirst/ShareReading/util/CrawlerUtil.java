package com.TheWorldFirst.ShareReading.util;

import org.openqa.selenium.By;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.*;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

public class CrawlerUtil {

    //浏览器位置
    private static final String browserFireFox = "D:\\FireFox\\firefox.exe";

    //geckodriver位置
    private static final String geckodriver = "src/main/resources/static/util/geckodriver.exe";

    //等待时间
    private static int waitTime = 3;

    /**
     *获取火狐浏览器配置信息
     * @return
     */
    private static FirefoxOptions getFirefoxOptions() {
        File pathToBinary = new File(browserFireFox);
        FirefoxBinary firefoxBinary = new FirefoxBinary(pathToBinary);
        firefoxBinary.addCommandLineOptions("--headless");
        System.setProperty("webdriver.gecko.driver", geckodriver);
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        //设置不加载css和图片
        firefoxOptions.setPageLoadStrategy(PageLoadStrategy.EAGER);
        firefoxOptions.setBinary(firefoxBinary);
        return firefoxOptions;
    }

    /**
     *获取火狐浏览器
     * @return
     */
    private static FirefoxDriver getFirefoxDriver() {
        FirefoxDriver firefoxDriver = new FirefoxDriver(getFirefoxOptions());
        return firefoxDriver;
    }

    /**
     *根据isbn查询书籍页面(根据火狐)
     * @param isbn
     * @return
     */
    public static String getBookUrlByFirefox(String isbn) {
        FirefoxDriver driver = getFirefoxDriver();
        driver.get("https://search.douban.com/book/subject_search?search_text=" + isbn + "&cat=1001");
        WebDriverWait wait = new WebDriverWait(driver, 3);
        WebElement firstResult = wait.until(presenceOfElementLocated(By.className("cover-link")));
        //得到内容之后立即停止继续加载
        driver.executeScript("window.stop();");
        String bookUrl = firstResult.getAttribute("href");
        return bookUrl;
    }

    /**
     *根据豆瓣url查询书籍信息(根据火狐)
     * @param url
     * @return
     */
    public static String getBookInfoByFirefox(String url) {
        FirefoxDriver driver = getFirefoxDriver();
        driver.get(url);
        WebDriverWait wait = new WebDriverWait(driver, waitTime);
        wait.until(presenceOfElementLocated(By.id("link-report")));
        //得到内容之后立即停止继续加载
        driver.executeScript("window.stop();");
        String pageSource = driver.getPageSource();
        return pageSource;
    }

    /**
     *根据豆瓣url获取书籍图片Base64(根据火狐)
     * @param url
     * @return
     */
    public static String getBookImageFirefox(String url) {
        FirefoxDriver driver = getFirefoxDriver();
        driver.get(url);
        WebDriverWait wait = new WebDriverWait(driver, waitTime);
        wait.until(presenceOfElementLocated(By.tagName("img")));
        driver.executeScript("window.stop();");
        String imgBase64 = "";
        try {
            driver.executeScript("function getBase64(imgUrl) { window.URL = window.URL; let xhr = new XMLHttpRequest(); xhr.open('get', imgUrl, true); xhr.responseType = 'blob'; xhr.onload = function () { if (this.status == 200) { let blob = this.response; let oFileReader = new FileReader(); oFileReader.onloadend = function (e) { let base64 = e.target.result; document.body.innerHTML = base64; }; oFileReader.readAsDataURL(blob); }; }; xhr.send(); }; getBase64(arguments[0]);", url);
            Thread.sleep(1000);
            imgBase64 = driver.findElement(By.tagName("body")).getText();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.quit();
        return imgBase64;
    }
}
