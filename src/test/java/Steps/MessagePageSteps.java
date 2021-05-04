package Steps;

import interfaces.IMessagePage;
import io.cucumber.java.en.And;
import modal_windows.SendEmailModalWindow;
import org.junit.Assert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pages.MainPage;
import utils.DriverSingleton;

public class MessagePageSteps extends Steps {
    private IMessagePage iMessagePage;

    @And("^I open this message and check that sender = (.*), subject = (.*) and text = (.*)$")
    public void iOpenThisMessageAndCheckSenderSubjectAndText(String emailSender, String emailSubject, String emailText) {
        iMessagePage = new MainPage().readMessage();
        if(emailText.equals("secret address")) {
            emailText = MainPageSteps.secretAddress;
        }
        if(emailSender.equals("myself")) {
            emailSender = MainPageSteps.myEmail;
        }
        log.info("Checking than received email has sender: " + MainPageSteps.myEmail + ", subject: " + emailSubject + ", text: " + emailText);
        Assert.assertEquals("Sender address mismatch",
                emailSender,
                iMessagePage.getSender().getAttribute("textContent"));

        Assert.assertEquals("Email theme mismatch",
                emailSubject,
                iMessagePage.getMailSubject().getAttribute("textContent"));

        Assert.assertEquals("Email text mismatch",
                emailText,
                iMessagePage.getMailText().getAttribute("textContent"));
    }

    @And("^I reply on email with the text \"(.*)\"$")
    public void iReplyOnEmailWithTheText(String emailText) {
        log.info("Opening of the write message modal window...");
        SendEmailModalWindow modal = iMessagePage.reply();
        wait.until(ExpectedConditions.visibilityOf(modal.getModalWindow()));
        Assert.assertTrue("New message modal window is not displayed",
                modal.getModalWindow().isDisplayed());
        log.info("Reply message with text: " + emailText);
        modal.getMsgTextInput().sendKeys(emailText);
        modal.getSubmitBtn().click();
        if (modal.getModalWindow().isDisplayed()) {
            modal.getCloseBtn().click();
        }
        log.info("Waiting for invisibility of modal window...");
        wait.until(ExpectedConditions.invisibilityOf(modal.getModalWindow()));
        log.info("Go back to the Main page");
        DriverSingleton.getDriver().navigate().back();
    }

    @And("^I delete this message$")
    public void iDeleteThisMessage() {
        log.info("I open delete modal window");
        iMessagePage.getDeleteMsgBtn().click();
        wait.until(ExpectedConditions.visibilityOf(iMessagePage.getConfirmDeleteModal()));
        log.info("I confirm message deletion");
        iMessagePage.getConfirmDeleteBtn().click();
    }
}
