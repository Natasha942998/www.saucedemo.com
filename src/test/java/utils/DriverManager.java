package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.concurrent.ConcurrentHashMap;

public class DriverManager {
    private static final ConcurrentHashMap<String, WebDriver> drivers = new ConcurrentHashMap<>();

    public static WebDriver getDriver(String browser) {
        String threadId = Thread.currentThread().getName();
        if (!drivers.containsKey(threadId)) {
            WebDriver driver;
            switch (browser.toLowerCase()) {
                case "chrome":
                    driver = new ChromeDriver();
                    break;
                case "firefox":
                    driver = new FirefoxDriver();
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported browser: " + browser);
            }
            driver.manage().window().maximize();
            drivers.put(threadId, driver);
        }
        return drivers.get(threadId);
    }

    public static void quitDriver() {
        String threadId = Thread.currentThread().getName();
        WebDriver driver = drivers.remove(threadId);
        if (driver != null) {
            driver.quit();
        }
    }
}