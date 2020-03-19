package com.TheWorldFirst.ShareReading;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;

@SpringBootTest
class ShareReadingApplicationTests {

	@Test
	void contextLoads() {
		File pathToBinary = new File("D:\\FireFox\\firefox.exe");
		FirefoxBinary firefoxBinary = new FirefoxBinary(pathToBinary);
		firefoxBinary.addCommandLineOptions("--headless");
		System.setProperty("webdriver.gecko.driver", "src/main/resources/static/util/geckodriver.exe");
		FirefoxOptions firefoxOptions = new FirefoxOptions();
		firefoxOptions.setBinary(firefoxBinary);
		FirefoxDriver driver = new FirefoxDriver(firefoxOptions);
		driver.get("https://book.douban.com/subject/34846908/");
		String title = driver.getTitle();
		String html = driver.getPageSource();
		System.out.println(html);
		System.out.println(title);
		driver.quit();
	}

}
