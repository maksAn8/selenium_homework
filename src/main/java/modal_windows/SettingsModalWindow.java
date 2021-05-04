package modal_windows;

import interfaces.IModalWindow;
import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.DriverSingleton;

@Getter
public class SettingsModalWindow implements IModalWindow {
    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(css = "#secret-address")
    private WebElement secretAddress;

    @FindBy(css = "#modal-settings .modal-content .bar .close")
    private WebElement closeBtn;

    public SettingsModalWindow() {
        driver = DriverSingleton.getDriver();
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, 20);
    }

    public void closeModalWindow() {
        wait.until(ExpectedConditions.elementToBeClickable(closeBtn)).click();
        wait.until(ExpectedConditions.invisibilityOf(secretAddress));
    }
}
