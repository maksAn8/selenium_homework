package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.concurrent.TimeUnit;

public final class DriverSingleton {
    private static WebDriver driver;

    private DriverSingleton() {}

    public static WebDriver getDriver() {
        if(driver == null) {
            Property property = new Property();
            ChromeOptions options = new ChromeOptions();
            options.addArguments("start-maximized");
            System.setProperty(property.getChromeDriver(), "./drivers/chromedriver.exe");
            driver = new ChromeDriver(options);
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        }
        return driver;
    }

    public static void quit() {
        driver.quit();
    }
}
