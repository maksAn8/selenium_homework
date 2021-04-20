package pages;

import lombok.Getter;
import modal_windows.SendEmailModalWindow;
import modal_windows.SettingsModalWindow;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.DriverSingleton;
import utils.Property;

@Getter
public class MainPage {
    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(css = "#pre_rand")
    private WebElement randomNameBtn;

    @FindBy(css = "#domain")
    private WebElement domainName;

    @FindBy(css = "#pre_button")
    private WebElement randomNameInput;

    @FindBy(css = "div.dropdown-menu button:nth-of-type(6)")
    private WebElement domainDropDownItem;

    @FindBy(css = "#pre_settings")
    private WebElement settingsBtn;

    @FindBy(css = "#container-body .ml-3")
    private WebElement infoTextContainer;

    @FindBy(css = "#compose")
    private WebElement writeMsgBtn;

    @FindBy(css = "#container-body .inbox .mail")
    private WebElement mail;

    @FindBy(css = ".bar span[data-tr='settings']")
    private WebElement settingsModalWindow;

    @FindBy(css = ".bar span[data-tr='new_message']")
    private WebElement writeMessageModalWindow;

    public MainPage() {
        driver = DriverSingleton.getDriver();
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, 20);
        Property property = new Property();
        driver.get(property.getAppMainPageUrl());
    }

    public SettingsModalWindow goToSettings() {
        settingsBtn.click();
        wait.until(ExpectedConditions.visibilityOf(settingsModalWindow));
        return new SettingsModalWindow();
    }

    public SendEmailModalWindow writeMessage() {
        writeMsgBtn.click();
        wait.until(ExpectedConditions.visibilityOf(writeMessageModalWindow));
        return new SendEmailModalWindow();
    }

    public MessagePage readMessage() {
        mail.click();
        wait.until(ExpectedConditions.invisibilityOf(mail));
        return new MessagePage();
    }
}
