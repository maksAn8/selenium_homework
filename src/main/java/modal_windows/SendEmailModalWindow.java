import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

@Getter
public class SendEmailModalWindow {
    private WebDriver driver;
    private WebDriverWait wait;

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

    public SendEmailModalWindow() {
        driver = DriverSingleton.getDriver();
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, 20);
    }
}
