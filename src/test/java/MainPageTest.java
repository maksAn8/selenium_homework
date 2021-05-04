import modal_windows.SendEmailModalWindow;
import modal_windows.SettingsModalWindow;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.MainPage;
import pages.MessagePage;
import utils.DriverSingleton;

public class MainPageTest {
    private final MainPage mainPage = new MainPage();;
    private final WebDriverWait wait = new WebDriverWait(DriverSingleton.getDriver(), 20);

    @Test
    public void setEmailAndSendMessage() {
        String email = getRandomEmail();
        String secretAddress = getSecretAddress();

        checkInfoMessage();

        goToSendEmailModal(email, secretAddress);

        readMessage(email, "Test", secretAddress);
        reply();

        wait.until(ExpectedConditions.textToBePresentInElement(mainPage.getSender(), email));

        readMessage(email, "Re: Test", "Test2");

        deleteMessage();

        Assert.assertFalse("Message was not deleted",
                DriverSingleton.getDriver().findElements(By.cssSelector("#container-body .inbox .mail .text-truncate")).
                        stream().anyMatch(webElement -> webElement.getAttribute("textContent").equals("Re: Test")));
    }

    @AfterClass
    public static void tearDown() {
        DriverSingleton.quit();
    }

    private void checkInfoMessage() {
        Assert.assertEquals("Info message mismatch",
                "В ожидании новых писем...",
                mainPage.getInfoTextContainer().getAttribute("textContent"));

        Assert.assertTrue("Text is not visible", mainPage.getInfoTextContainer().isDisplayed());
    }

    private String getRandomEmail() {
        mainPage.getRandomNameBtn().click();
        mainPage.getDomainName().click();
        wait.until(ExpectedConditions.visibilityOf(mainPage.getDomainDropDownItem())).click();
        return mainPage.getRandomNameInput().getAttribute("value") + mainPage.getDomainName().getText();
    }

    private String getSecretAddress() {
        SettingsModalWindow modal = mainPage.goToSettings();
        String secretAddress = modal.getSecretAddress().getText();
        modal.closeModalWindow();
        Assert.assertFalse("Settings modal window is displayed",
                mainPage.getSettingsModalWindow().isDisplayed());
        return secretAddress;
    }

    private void goToSendEmailModal(String receiver, String emailText) {
        SendEmailModalWindow modal = mainPage.writeMessage();

        Assert.assertTrue("New message modal window is not displayed",
                mainPage.getWriteMessageModalWindow().isDisplayed());

        sendMessage(modal, receiver, emailText);

        if (mainPage.getWriteMessageModalWindow().isDisplayed()) {
            modal.getCloseBtn().click();
        }
    }

    private void readMessage(String sender, String subject, String mailText) {
        wait.until(ExpectedConditions.visibilityOf(mainPage.getMail()));
        MessagePage messagePage = mainPage.readMessage();
        Assert.assertEquals("Sender address mismatch",
                sender,
                messagePage.getSender().getAttribute("textContent"));

        Assert.assertEquals("Email theme mismatch",
                subject,
                messagePage.getMailSubject().getAttribute("textContent"));

        Assert.assertEquals("Email text mismatch",
                mailText,
                messagePage.getMailText().getAttribute("textContent"));
    }

    private void reply() {
        SendEmailModalWindow modal = new MessagePage().reply();
        String emailText = "Test2";
        sendMessage(modal, null, emailText);
        wait.until(ExpectedConditions.invisibilityOf(mainPage.getWriteMessageModalWindow()));
        DriverSingleton.getDriver().navigate().back();
    }

    private void sendMessage(SendEmailModalWindow modal, String receiver, String emailText) {
        wait.until(ExpectedConditions.visibilityOf(mainPage.getWriteMessageModalWindow()));
        if(receiver != null) {
            modal.getToInput().sendKeys(receiver);
            modal.getSubjectInput().sendKeys("Test");
        }
        modal.getMsgTextInput().sendKeys(emailText);
        modal.getSubmitBtn().click();
    }

    private void deleteMessage() {
        MessagePage messagePage = new MessagePage();
        messagePage.getDeleteMsgBtn().click();
        wait.until(ExpectedConditions.visibilityOf(messagePage.getConfirmDeleteModal()));
        messagePage.getConfirmDeleteBtn().click();
    }
}
