package pages;

import lombok.Getter;
import modal_windows.SendEmailModalWindow;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.DriverSingleton;

@Getter
public class MessagePage {
    private WebDriverWait wait;
    @FindBy(css = "#info .text-truncate")
    private WebElement sender;

    @FindBy(css = "#info .subject.mb-20")
    private WebElement mailSubject;

    @FindBy(css = "div.overflow-auto.mb-20")
    private WebElement mailText;

    @FindBy(id = "reply")
    private WebElement replyBtn;

    @FindBy(id = "delete_mail")
    private WebElement deleteMsgBtn;

    @FindBy(css = ".modal-title[data-tr='delete_confirm']")
    private WebElement confirmDeleteModal;

    @FindBy(id = "confirm_mail")
    private WebElement confirmDeleteBtn;

    public MessagePage() {
        wait = new WebDriverWait(DriverSingleton.getDriver(), 20);
        PageFactory.initElements(DriverSingleton.getDriver(), this);
    }

    public SendEmailModalWindow reply() {
        replyBtn.click();
        return new SendEmailModalWindow();
    }
}
