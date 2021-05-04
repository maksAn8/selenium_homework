package interfaces;

import modal_windows.SendEmailModalWindow;
import modal_windows.SettingsModalWindow;
import org.openqa.selenium.WebElement;
import pages.MessagePage;

public interface IMainPage extends IPage {
    WebElement getRandomNameBtn();

    WebElement getDomainName();

    WebElement getRandomNameInput();

    WebElement getDomainDropDownItem();

    WebElement getSettingsBtn();

    WebElement getInfoTextContainer();

    WebElement getWriteMsgBtn();

    WebElement getMail();

    WebElement getSender();

    WebElement getSubject();

    WebElement getSettingsModalWindow();

    WebElement getWriteMessageModalWindow();

    SettingsModalWindow goToSettings();

    SendEmailModalWindow writeMessage();

    MessagePage readMessage();
}
