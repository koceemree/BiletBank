package com.biletbank.pages;

import com.biletbank.utilities.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class RegistrationPage {
    public RegistrationPage() {
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(css = "a.new-agency-btn[href*='AgencyRegistration']")
    public WebElement newAgentMembershipBtn;

    @FindBy(css = "label[for='acceptContract']")
    public WebElement sozlesmeKabulLabel;

    @FindBy(css = "#agencyform a.bb-btn--blue")
    public WebElement continueBtn;

    @FindBy(css = ".checkAgencyInvalid input.bltbnk-textbox")
    public WebElement agenteNameInput;
    // Acente Bilgileri - Mail Adresi (Max 60 Karakter)
    @FindBy(css = "div[class*='bb-input'] input[maxlength='60']")
    public WebElement mailInput;

    @FindBy(css = "input.invalid")
    public List<WebElement> invalidInputs;

    // --- ORTAK ELEMENT ---
    @FindBy(css = ".ivu-select-visible input.ivu-select-input")
    public WebElement countryCodeSearchBox;

    // --- SABİT TELEFON ---
    @FindBy(xpath = "//div[text()='Sabit Telefonu']/parent::div//div[contains(@class,'ivu-select-selection')]")
    public WebElement landlineCountryDropdown;

    @FindBy(xpath = "//div[text()='Sabit Telefonu']/parent::div//input[contains(@class,'bltbnk-textbox')]")
    public WebElement landlinePhoneInput;

    // --- CEP TELEFONU ---
    @FindBy(xpath = "//div[text()='Cep Telefonu']/parent::div//div[contains(@class,'ivu-select-selection')]")
    public WebElement mobileCountryDropdown;

    @FindBy(xpath = "//div[text()='Cep Telefonu']/parent::div//input[contains(@class,'bltbnk-textbox')]")
    public WebElement mobilePhoneInput;

    // --- FAX NO ---
    @FindBy(xpath = "//div[@name='txtFaxPhonePrefix']//input[contains(@class,'bltbnk-textbox')]")
    public WebElement faxCountryDropdown;

    @FindBy(xpath = "//div[div[normalize-space()='Fax No']]//div[@class='col-8 col-md-9']//input")
    public WebElement faxPhoneInput;
    @FindBy(xpath = "//div[div[div[normalize-space()='Türsab Belge No']]]//input[@maxlength='30']")
    public WebElement tursabNoInput;
    @FindBy(xpath = "(//select[@class='bb-select type3 mw-100 h36'])[1]")
    public WebElement agencyClassDropdown;
    // Ülke seçimi her zaman select etiketiyle kalıyor
    @FindBy(xpath = "//div[text()='Ülke']/parent::div//select")
    public WebElement countryDropdown;

    // Şehir alanı Türkiye seçiliyken aktif olan select
    @FindBy(xpath = "//select[@class='bb-select type3 bordered mw-100 h36']")
    public WebElement cityDropdown;

    // Şehir alanı Türkiye dışı bir ülke seçildiğinde aktif olan input
    @FindBy(xpath = "//div[text()='Şehir']/parent::div//input")
    public WebElement cityInput;
    // İlçe başlığı altındaki asıl input alanı
    @FindBy(xpath = "//select[preceding-sibling::div[normalize-space()='İlçe'] or ../preceding-sibling::div[normalize-space()='İlçe']]")
    public WebElement districtInput;
    @FindBy(xpath = "//div[@class='w-100 fs12'][normalize-space()='Semt']/following-sibling::div//input")
    public WebElement semptInput;

    @FindBy(xpath = "//input[ancestor::div[div[@class='w-100 fs12'][normalize-space()='Cadde / Bulvar']]]")
    public WebElement streetInput;
    @FindBy(xpath = "//input[ancestor::div[div[@class='w-100 fs12'][normalize-space()='Adres Detayı']]]")
    public WebElement addressDetailInput;
    @FindBy(xpath = "//textarea[ancestor::div[div[@class='w-100 fs12'][normalize-space()='Yol Tarifi']]]")
    public WebElement directionsInput;

    @FindAll({
            @FindBy(xpath = "//input[@id='activityAreasFlightSeller']"),
            @FindBy(xpath = "//input[@id='activityAreasHotelSeller']"),
            @FindBy(xpath = "//input[@id='activityAreasBusSeller']"),
            @FindBy(xpath = "//input[@id='activityAreasTourSeller']"),
            @FindBy(xpath = "//input[@id='activityAreasRentACarSeller']"),
            @FindBy(xpath = "//input[@id='activityAreasFlightSeller']")
    })
    public List<WebElement> activityAreasCheckboxes;

    @FindBy(xpath = "//div[text()='Fatura Ünvanı']/parent::div//input[contains(@class,'bltbnk-textbox')]")
    public WebElement billingTitleInput;

    // Fatura Bilgileri - Vergi Dairesi Input Alanı
    @FindBy(xpath = "//div[text()='Vergi Dairesi']/parent::div//input[contains(@class,'bltbnk-textbox')]")
    public WebElement taxOfficeInput;

    @FindBy(xpath = "//input[ancestor::div[div[@class='w-100 fs12'][normalize-space()='Vergi Numarası']]]")
    public WebElement taxNumberInput;

    @FindBy(xpath = "//input[ancestor::div[div[@class='w-100 fs12'][normalize-space()='Fatura Adresi']]]")
    public WebElement billingAddressInput;
    @FindBy(xpath = "//input[ancestor::div[div[@class='w-100 fs12'][normalize-space()='Fatura Şehri']]]")
    public WebElement billingCityInput;
    @FindBy(xpath = "//input[ancestor::div[div[@class='w-100 fs12'][normalize-space()='IBAN']]]")
    public WebElement ibanInput;

    @FindBy(xpath = "//div[@class='w-100 fs12'][normalize-space()='Banka İsmi']/following-sibling::div//input")
    public WebElement bankNameInput;

    @FindBy(xpath = "//input[ancestor::div[div[@class='w-100 fs12'][normalize-space()='Şirket Yetkilisi Adı ve Soyadı']]]")
    public WebElement companyOfficialInput;
    @FindBy(xpath = "//div[@name='txtManagerMobilePhonePrefix']//following-sibling::div[@class='col-8 col-md-9']//input")
    public WebElement adminMobileInput;
    @FindBy(xpath = "//div[div[@class='box-title color-black'][normalize-space()='Yönetici Bilgileri']]//input[@maxlength='60']")
    public WebElement adminMailInput;
    @FindBy(xpath = "//input[ancestor::div[div[@class='w-100 fs12'][normalize-space()='Kullanıcı Adı']]]")
    public WebElement usernameInput;


    @FindBy(xpath = "//div[div[@class='box-title color-black'][normalize-space()='Kullanıcı Giriş Bilgileri']]//input[@maxlength='128']")
    public WebElement loginMailInput;
    @FindBy(xpath = "//input[@id='password']")
    public WebElement passwordInput;

    @FindBy(xpath = "//button[text()='Kaydet']")
    public WebElement saveButton;
    @FindBy(id = "main-validation-messages")
    public WebElement passwordValidationContainer;
    @FindBy(css = ".bb-validation-message, .field-validation-error, span.invalid-feedback")
    public List<WebElement> fieldValidationMessages;
}
