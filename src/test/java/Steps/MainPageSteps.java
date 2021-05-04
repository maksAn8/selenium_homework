package Steps;

import interfaces.IMainPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import modal_windows.SendEmailModalWindow;
import modal_windows.SettingsModalWindow;
import org.junit.Assert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pages.MainPage;
import utils.DriverSingleton;
import utils.Property;

public class MainPageSteps extends Steps {
    private IMainPage iMainPage;
    private String secretAddress;
    private String myEmail;

    @Given("^I open Main page$")
    public void iOpenMainPage() {
        Property property = new Property();
        log.info("Main page opening");
        DriverSingleton.getDriver().get(property.getAppMainPageUrl());
        iMainPage = new MainPage();
    }


    @And("^I get random email name$")
    public void iGetRandomEmailName() {
        log.info("Click random name button");
        iMainPage.getRandomNameBtn().click();
    }

    @And("^I select email domain$")
    public void iSelectEmailDomain() {
        log.info("Select email domain from dropdown");
        iMainPage.getDomainName().click();
        wait.until(ExpectedConditions.visibilityOf(iMainPage.getDomainDropDownItem())).click();
    }

    @Then("^I check that my domain is (.*)$")
    public void iCheckThatMyDomainIsRoverInfo(String domainName) {
        log.info("Assertion of actual domain with expected domain = " + domainName);
        Assert.assertEquals("Domain mismatch",
                domainName,
                iMainPage.getDomainName().getText());
        log.info("Remember user email address");
        myEmail = iMainPage.getRandomNameInput().getAttribute("value") + iMainPage.getDomainName().getText();
    }

    @And("^I open email settings and remember secret address$")
    public void iOpenEmailSettingsAndRememberSecretAddress() {
        log.info("Settings modal window opening...");
        SettingsModalWindow modal = iMainPage.goToSettings();
        secretAddress = modal.getSecretAddress().getText();
        modal.closeModalWindow();
        log.info("Settings modal window is closed.");
    }

    @Then("^I check than settings modal window is closed$")
    public void iCheckThanSettingsModalWindowIsClosed() {
        log.info("Assertion of settings modal window invisibility...");
        Assert.assertFalse("Settings modal window is displayed.",
                iMainPage.getSettingsModalWindow().isDisplayed());
    }

    @And("^I check info message \"(.*)\" on the Main Page$")
    public void iCheckInfoMessageOnTheMainPage(String infoMessage) {
        log.info("Checking info message when user doesn't have any emails...");
        Assert.assertEquals("Info message mismatch",
                infoMessage,
                iMainPage.getInfoTextContainer().getAttribute("textContent"));
        log.info("Asserting that info message is visible...");
        Assert.assertTrue("Text is not visible", iMainPage.getInfoTextContainer().isDisplayed());
    }

    @And("^I send email to (.*) with a subject \"(.*)\" and (.*) as email text$")
    public void iSendEmailToMyselfWithASubjectAndSecretAddressAsEmailText(String emailReceiver, String emailSubject, String emailText) {
        log.info("Opening of the write message modal window...");
        SendEmailModalWindow modal = iMainPage.writeMessage();
        Assert.assertTrue("New message modal window is not displayed",
                iMainPage.getWriteMessageModalWindow().isDisplayed());
        log.info("Sending message to: " + myEmail + ", subject: " + emailSubject + ", text: " + emailText);
        if(emailReceiver.equals("myself")) {
            modal.getToInput().sendKeys(myEmail);
        } else {
            modal.getToInput().sendKeys(emailReceiver);
        }
        modal.getSubjectInput().sendKeys(emailSubject);
        modal.getMsgTextInput().sendKeys(emailText);
        modal.getSubmitBtn().click();
        if (iMainPage.getWriteMessageModalWindow().isDisplayed()) {
            modal.getCloseBtn().click();
        }
    }

    @Then("^I wait for message with sender: (.*), subject: (.*)$")
    public void iWaitForMessageWithTheseParams(String emailSender, String emailSubject) {
        if(emailSender.equals("myself")) {
            emailSender = myEmail;
        }
        log.info("Waiting for the message with sender = " + emailSender + "subject = " + emailSubject);
        wait.until(ExpectedConditions.visibilityOf(iMainPage.getMail()));
        Assert.assertEquals("Sender address mismatch",
                emailSender,
                iMainPage.getSender().getAttribute("textContent"));

        Assert.assertEquals("Email theme mismatch",
                emailSubject,
                iMainPage.getSubject().getAttribute("textContent"));
    }
}
