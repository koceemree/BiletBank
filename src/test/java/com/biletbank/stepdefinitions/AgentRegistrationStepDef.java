package com.biletbank.stepdefinitions;

import com.biletbank.pages.RegistrationPage;
import com.biletbank.utilities.ConfigReader;
import com.biletbank.utilities.GoogleSheetsUtils;
import com.biletbank.utilities.ReusableMethods;
import helpers.RegistrationHelper;
import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.time.Duration;
import java.util.List;
import java.util.Map;

import static com.biletbank.utilities.Driver.getDriver;
import static com.biletbank.utilities.ReusableMethods.*;

public class AgentRegistrationStepDef {
    RegistrationPage registrationPage = new RegistrationPage();
    SoftAssert softAssert = new SoftAssert();
    WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
    @Given("The user is on the BiletBank login page")
    public void theUserIsOnTheBiletBankLoginPage() throws InterruptedException {
        getDriver().get(ConfigReader.getProperty("url"));
        verifyFaviconExists(new SoftAssert());
    }

    @When("The user clicks on the new agent registration button")
    public void theUserClicksOnTheNewAgentRegistrationButton() throws InterruptedException {
        registrationPage.newAgentMembershipBtn.click();

    }

    @Then("The user should be redirected to the contract page")
    public void theUserShouldBeRedirectedToTheContractPage() {
        switchToNewWindow();
        try {
            waitForVisibility(registrationPage.sozlesmeKabulLabel);
            softAssert.assertTrue(registrationPage.sozlesmeKabulLabel.isDisplayed());
        } catch (StaleElementReferenceException e) {
            waitForVisibility(registrationPage.sozlesmeKabulLabel);
            softAssert.assertTrue(registrationPage.sozlesmeKabulLabel.isDisplayed());
        }
        softAssert.assertAll();
    }

    @When("The user selects the contract checkbox")
    public void theUserSelectsTheContractCheckbox() {
        registrationPage.sozlesmeKabulLabel.click();
    }

    @And("The user clicks the continue button")
    public void theUserClicksTheContinueButton() {
        ReusableMethods.waitForClickable(registrationPage.continueBtn);
        registrationPage.continueBtn.click();
    }

    @Then("The user should be redirected to the registration form page")
    public void theUserShouldBeRedirectedToTheRegistrationFormPage() {
        waitForVisibility(registrationPage.agenteNameInput);
        softAssert.assertTrue(registrationPage.agenteNameInput.isDisplayed());
        softAssert.assertAll();
    }

    @When("The user fills out the registration form with valid parametric data")
    public void theUserFillsOutTheRegistrationFormWithValidParametricData() throws InterruptedException {
        List<Map<String, String>> allTestData = GoogleSheetsUtils.getSheetDataAsMap("Sayfa1");
        RegistrationHelper registrationHelper = new RegistrationHelper();

        for (int i = 0; i < allTestData.size(); i++) {
            String suffix = String.format("%02d", i + 1);
            registrationHelper.fillFormWithGoogleSheetsData(allTestData.get(i), suffix);

        }

    }

    @And("The user selects the required checkboxes for agency activities")
    public void theUserSelectsTheRequiredCheckboxesForAgencyActivities() {
    }

    @And("The user clicks the save button")
    public void theUserClicksTheSaveButton() {
        scrollToElement(registrationPage.saveButton);
        registrationPage.saveButton.click();
    }

    @Then("The user should verify the successful registration message")
    public void theUserShouldVerifyTheSuccessfulRegistrationMessage() {
    }

    @Given("The user is on the contract page")
    public void theUserIsOnTheContractPage() {
        waitForVisibility(registrationPage.sozlesmeKabulLabel);
        softAssert.assertTrue(registrationPage.sozlesmeKabulLabel.isDisplayed());
        softAssert.assertAll();
    }

    @When("The user leaves the contract checkbox unchecked")
    public void theUserLeavesTheContractCheckboxUnchecked() {
    }


    @Then("The system should display a validation error for the contract")
    public void theSystemShouldDisplayAValidationErrorForTheContract() {
        softAssert.fail("Bir Uyarı mesajı bulunmamaktadır");
        softAssert.assertAll();
    }

    @And("The user should not be redirected to the registration form page")
    public void theUserShouldNotBeRedirectedToTheRegistrationFormPage() {
        softAssert.assertTrue(registrationPage.sozlesmeKabulLabel.isDisplayed(),"Kullanıcının kayıt form sayfasına yönlendirildiği görüldü");
        softAssert.assertAll();
    }





    @Given("The user is on the registration form page directly after accepting the contract")
    public void theUserIsOnTheRegistrationFormPageDirectlyAfterAcceptingTheContract() {
        waitForVisibility(registrationPage.sozlesmeKabulLabel);
        registrationPage.sozlesmeKabulLabel.click();
        waitForClickable(registrationPage.continueBtn);
        registrationPage.continueBtn.click();
        waitForVisibility(registrationPage.agenteNameInput);
        softAssert.assertTrue(registrationPage.agenteNameInput.isDisplayed());
        System.out.println("📝 Kayıt form sayfasına geçildi.");
    }

    @When("The user leaves mandatory fields empty")
    public void theUserLeavesMandatoryFieldsEmpty() {
        // Alanlar zaten boş geldiği için ek işlem gerekmez
        System.out.println("🔲 Tüm zorunlu alanlar boş bırakıldı.");
    }

    @Then("The user should see validation error messages under the required fields")
    public void theUserShouldSeeValidationErrorMessagesUnderTheRequiredFields() {

        wait.until(driver -> !registrationPage.invalidInputs.isEmpty());

        int invalidCount = registrationPage.invalidInputs.size();
        Assert.assertTrue(
                invalidCount > 0,
                "Zorunlu alanlarda en az 1 hata işareti olmalıdır! Bulunan: " + invalidCount
        );
        System.out.println("✅ Hata işaretli alan sayısı: " + invalidCount);

        // Varsa hata mesajlarını logla
        List<WebElement> messages = registrationPage.fieldValidationMessages;
        if (!messages.isEmpty()) {
            System.out.println("📋 Görünen hata mesajları:");
            messages.forEach(msg -> System.out.println("   - " + msg.getText()));
        }
    }

    @And("The registration process should not be completed")
    public void theRegistrationProcessShouldNotBeCompleted() {
        List<WebElement> successElements = getDriver().findElements(
                By.cssSelector(".swal2-popup, .bb-success-message, .alert-success, .success-modal")
        );
        boolean successVisible = successElements.stream()
                .anyMatch(el -> {
                    try { return el.isDisplayed(); } catch (Exception e) { return false; }
                });

        Assert.assertFalse(
                successVisible,
                "Geçersiz veri ile kayıt tamamlanmamalıdır! Başarı mesajı görünmemelidir."
        );
        Assert.assertTrue(
                registrationPage.agenteNameInput.isDisplayed(),
                "Kayıt tamamlanmadığı için kullanıcı hâlâ form sayfasında olmalıdır!"
        );
        System.out.println("✅ Kayıt işlemi tamamlanmadı. Kullanıcı form sayfasında kaldı.");
    }

    @When("the user enters an invalid email format")
    public void theUserEntersAnInvalidEmailFormat() {
        getDriver().navigate().refresh();
        waitForVisibility(registrationPage.sozlesmeKabulLabel);
        registrationPage.sozlesmeKabulLabel.click();
        waitForClickable(registrationPage.continueBtn);
        registrationPage.continueBtn.click();
        waitForVisibility(registrationPage.agenteNameInput);
        softAssert.assertTrue(registrationPage.agenteNameInput.isDisplayed());
        String invalidEmail = "invalidemail@";
        ReusableMethods.scrollToElement(registrationPage.mailInput);
        registrationPage.mailInput.clear();
        registrationPage.mailInput.sendKeys(invalidEmail);

        // Validasyonu tetiklemek için farklı bir alana tıkla
        registrationPage.agenteNameInput.click();
    }

    @Then("the user should see an invalid email format warning")
    public void theUserShouldSeeAnInvalidEmailFormatWarning() {
        SoftAssert softAssert = new SoftAssert();
        boolean warningFound = false;

        // Kontrol 1: Mail input'unun invalid class almasını bekle ve kontrol et
        try {

            wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.cssSelector("div[class*='bb-input'] input[maxlength='60'].invalid")
            ));
            String mailClass = registrationPage.mailInput.getAttribute("class");
            if (mailClass != null && mailClass.contains("invalid")) {
                warningFound = true;
                System.out.println("✅ Mail input 'invalid' class aldı.");
            }
        } catch (Exception e) {
            System.out.println("⚠️ Mail input invalid class beklentisi karşılanmadı: " + e.getMessage());
        }

        // Kontrol 2: Mail alanının yanındaki hata mesajı elementini kontrol et
        try {
            List<WebElement> emailErrors = getDriver().findElements(By.xpath(
                    "//input[@maxlength='60']/following-sibling::*[contains(@class,'error') " +
                            "or contains(@class,'invalid') or contains(@class,'validation')]"
            ));
            if (!emailErrors.isEmpty() && emailErrors.get(0).isDisplayed()) {
                warningFound = true;
                System.out.println("✅ Email hata mesajı görüntülendi: " + emailErrors.get(0).getText());
            }
        } catch (Exception e) {
            System.out.println("⚠️ Email hata mesajı elementi bulunamadı: " + e.getMessage());
        }

        softAssert.assertTrue(
                warningFound,
                "Geçersiz email formatı girildiğinde bir uyarı mesajı gösterilmelidir!"
        );
        softAssert.assertAll();
    }

    @And("the user verifies that mandatory fields are highlighted as invalid")
    public void theUserVerifiesThatMandatoryFieldsAreHighlightedAsInvalid() {

        List<WebElement> invalidFields = registrationPage.invalidInputs;
        softAssert.assertTrue(
                !invalidFields.isEmpty(),
                "En az 1 zorunlu alan 'invalid' olarak işaretlenmelidir!"
        );

        System.out.println("🔴 Invalid işaretli alanlar:");
        for (WebElement field : invalidFields) {
            String fieldName = field.getAttribute("name") != null
                    ? field.getAttribute("name")
                    : field.getAttribute("placeholder") != null
                    ? field.getAttribute("placeholder")
                    : field.getAttribute("id") != null
                    ? field.getAttribute("id")
                    : "bilinmeyen alan";

            String fieldClass = field.getAttribute("class");
            softAssert.assertTrue(
                    fieldClass != null && fieldClass.contains("invalid"),
                    "Alan 'invalid' class içermelidir! Alan: " + fieldName + " | Class: " + fieldClass
            );
            System.out.println("   ❌ " + fieldName + " → class: " + fieldClass);
        }
        softAssert.assertAll();
        System.out.println("✅ Tüm zorunlu alanlar invalid olarak işaretlendi.");
        softAssert.assertAll();
    }

    @Then("the user verifies character limits for agency name and mail")
    public void theUserVerifiesCharacterLimitsForAgencyNameAndMail() {
        String mailMaxLength = registrationPage.mailInput.getAttribute("maxlength");
        Assert.assertEquals(
                mailMaxLength, "60",
                "Mail alanı karakter sınırı 60 olmalıdır! Bulunan: " + mailMaxLength
        );
        System.out.println("✅ Mail alanı maxlength doğrulandı: " + mailMaxLength);
    }

}
