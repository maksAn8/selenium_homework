package interfaces;

import modal_windows.SendEmailModalWindow;
import org.openqa.selenium.WebElement;

public interface IMessagePage extends IPage {
    WebElement getSender();
    WebElement getMailSubject();
    WebElement getMailText();
    WebElement getReplyBtn();
    WebElement getDeleteMsgBtn();
    WebElement getConfirmDeleteModal();
    WebElement getConfirmDeleteBtn();
    SendEmailModalWindow reply();
}
