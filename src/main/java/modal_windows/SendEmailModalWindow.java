package modal_windows;

import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.DriverSingleton;

@Getter
public class SendEmailModalWindow {
    @FindBy(css = "#form .modal-header.shadow-sm span[data-tr='new_message']")
    private WebElement modalWindow;

    @FindBy(css = "#to")
    private WebElement toInput;

    @FindBy(css = "#subject")
    private WebElement subjectInput;

    @FindBy(css = "#text")
    private WebElement msgTextInput;

    @FindBy(css = "#submit")
    private WebElement submitBtn;

    @FindBy(css = "#modal-compose .modal-content .bar .close")
    private WebElement closeBtn;

    public SendEmailModalWindow() {
        PageFactory.initElements(DriverSingleton.getDriver(), this);
    }

}
