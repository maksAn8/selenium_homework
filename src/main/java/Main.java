
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;


public class Main {

    @Test
    public void testScenario1() {
        System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, 15);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        driver.get("https://tempmail.plus/ru/");

        WebElement randomName = driver.findElement(By.id("pre_rand"));
        randomName.click();

        WebElement domainName = driver.findElement(By.id("domain"));
        domainName.click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.dropdown-menu.show button:nth-of-type(6)"))).click();
        String emailName = driver.findElement(By.id("pre_button")).getAttribute("value") + domainName.getAttribute("innerText");

        WebElement settings = driver.findElement(By.id("pre_settings"));
        settings.click();

//        WebElement tenMinBtn = driver.findElement(By.cssSelector(".modal-body .btn[data-mins='10']"));
//        tenMinBtn.click();
//        driver.findElement(By.cssSelector(".modal-body input[data-tr='save']")).click();
//        settings.click();
//        Assert.assertEquals("Settings were not saved", "true", tenMinBtn.getAttribute("checked"));
//        driver.findElement(By.cssSelector(".modal-body input[data-tr='save']")).click();
//        wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.id("modal_settings"))));

        String secretAddress = driver.findElement(By.id("secret-address")).getAttribute("textContent");
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#modal-settings .modal-content .bar .close"))).click();

        Assert.assertFalse("Settings modal window is displayed",
                driver.findElement(By.cssSelector("div.modal-dialog.modal-dialog-scrollable")).isDisplayed());

        Assert.assertEquals("Info message mismatch",
                "В ожидании новых писем...",
                driver.findElement(By.cssSelector("#container-body .ml-3")).getAttribute("textContent"));
        Assert.assertTrue("Text is not visible", driver.findElement(By.cssSelector("#container-body .ml-3")).isDisplayed());

        driver.findElement(By.id("compose")).click();

        Assert.assertTrue("New message modal window is not displayed",
                driver.findElement(By.cssSelector("#form .modal-header.shadow-sm span[data-tr='new_message']")).isEnabled());

        driver.findElement(By.id("to")).sendKeys(emailName);
        driver.findElement(By.id("subject")).sendKeys("Test");
        driver.findElement(By.id("text")).sendKeys(secretAddress);
        driver.findElement(By.id("submit")).click();

        if (driver.findElement(By.cssSelector("#form .modal-header.shadow-sm span[data-tr='new_message']")).isEnabled()) {
            driver.findElement(By.cssSelector("#form .bar button.close")).click();
        }

        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#container-body .inbox .mail"))).click();

        Assert.assertEquals("Sender address mismatch",
                emailName,
                driver.findElement(By.cssSelector("#info .text-truncate")).getAttribute("textContent"));

        Assert.assertEquals("Email theme mismatch",
                "Test",
                driver.findElement(By.cssSelector("#info .subject.mb-20")).getAttribute("textContent"));

        Assert.assertEquals("Email text mismatch",
                secretAddress,
                driver.findElement(By.cssSelector("div.overflow-auto.mb-20")).getAttribute("textContent"));

        driver.findElement(By.id("reply")).click();
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector("#form .modal-header.shadow-sm span[data-tr='new_message']"))));
        driver.findElement(By.id("text")).sendKeys("Test2");
        driver.findElement(By.id("submit")).click();

        wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector("#form .modal-header.shadow-sm span[data-tr='new_message']"))));
        driver.navigate().back();

        driver.findElement(By.cssSelector("#container-body .inbox .mail .text-truncate")).click();

        Assert.assertEquals("Text message mismatch",
                "Test2",
                driver.findElement(By.cssSelector("div.overflow-auto.mb-20")).getAttribute("textContent"));

        driver.findElement(By.id("delete_mail")).click();
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector(".modal-title[data-tr='delete_confirm']"))));
        driver.findElement(By.id("confirm_mail")).click();

        Assert.assertFalse("Message was not deleted",
                driver.findElements(By.cssSelector("#container-body .inbox .mail .text-truncate")).
                        stream().anyMatch(webElement -> webElement.getAttribute("textContent").equals("Re: Test")));

        driver.quit();
    }

}
