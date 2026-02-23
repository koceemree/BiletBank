package helpers;

import com.biletbank.pages.RegistrationPage;
import com.biletbank.utilities.Driver;
import com.biletbank.utilities.GoogleSheetsUtils;
import com.biletbank.utilities.ReusableMethods;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.Map;

import static com.biletbank.utilities.ReusableMethods.*;
import static com.biletbank.utilities.ReusableMethods.selectFromDropdownByVisibleText;

public class RegistrationHelper {
    RegistrationPage registrationPage = new RegistrationPage();


    /**
     * Google Sheets'ten gelen Map verisini kullanarak formu doldurur.
     *
     * @param testData GoogleSheetsUtils'ten gelen satır verisi
     * @param suffix   Mülakat kuralı olan artan sayı (01, 02 vb.)
     */
    public void fillFormWithGoogleSheetsData(Map<String, String> testData, String suffix) throws InterruptedException {

        // --- ACENTE BİLGİLERİ ---
        if (testData.get("Acente Adi") != null) {
            String fullAgencyName = testData.get("Acente Adi") + suffix; // Test + 01 kuralı
            registrationPage.agenteNameInput.sendKeys(fullAgencyName);
        }

        if (testData.get("Mail Adresi") != null) {
            registrationPage.mailInput.sendKeys(testData.get("Mail Adresi"));
        }

        // Sabit Telefon (Ortak Metot Kullanımı)
        if (testData.get("Sabit Telefonu") != null)
            sendKeysFunction(registrationPage.landlinePhoneInput, testData.get("Sabit Telefonu"));

        // Cep Telefonu (Acente) (Ortak Metot Kullanımı)
        if (testData.get("Cep Telefonu") != null)
            sendKeysFunction(registrationPage.mobilePhoneInput, testData.get("Cep Telefonu"));

        // Fax No (Yeni Eklenen)
        if (testData.get("Fax No") != null)
            sendKeysFunction(registrationPage.faxPhoneInput, testData.get("Fax No"));

        if (testData.get("Tursab Belge No") != null) {
            sendKeysFunction(registrationPage.tursabNoInput, testData.get("Tursab Belge No"));
        }

        if (testData.get("Acente Sınıfı") != null) {
            waitForVisibility(registrationPage.agencyClassDropdown);
            selectFromDropdownByVisibleText(registrationPage.agencyClassDropdown, testData.get("Acente Sınıfı"));
        }
        if (testData.get("Ülke") != null) {
            String selectedCountry = testData.get("Ülke");
            selectFromDropdownByVisibleText(registrationPage.countryDropdown, testData.get("Ülke"));

            // Şehir verisi varsa işleme al
            if (testData.get("Sehir") != null) {

                if (selectedCountry.equalsIgnoreCase("Türkiye")) {
                    // Türkiye seçiliyse dropdown'dan seç
                    waitForVisibility(registrationPage.cityDropdown);
                    selectFromDropdownByVisibleText(registrationPage.cityDropdown, testData.get("Sehir"));
                } else {
                    // Türkiye dışı bir ülkeyse serbest metin olarak yaz
                    waitForVisibility(registrationPage.cityInput);
                    registrationPage.cityInput.clear();
                    sendKeysFunction(registrationPage.cityInput, testData.get("Sehir"));
                }
            }
        }

        if (testData.get("Ilce") != null) {
            waitForVisibility(registrationPage.districtInput);
            Thread.sleep(5000);
            selectFromDropdownByVisibleText(registrationPage.districtInput, (testData.get("Ilce")));
        }

        if (testData.get("Semt") != null) {
            sendKeysFunction(registrationPage.semptInput, testData.get("Semt"));
        }

        if (testData.get("Cadde Bulvar") != null) {
            sendKeysFunction(registrationPage.streetInput, testData.get("Cadde Bulvar"));
        }

        if (testData.get("Adres Detayi") != null) {
            sendKeysFunction(registrationPage.addressDetailInput, testData.get("Adres Detayi"));
        }

        if (testData.get("Yol Tarifi") != null) {
            sendKeysFunction(registrationPage.directionsInput, testData.get("Yol Tarifi"));
        }
        if (testData.get("Acente Faaliyet Alanları") != null) {
            selectActivityAreaCheckboxes(testData.get("Acente Faaliyet Alanları"));
        }
        // --- FATURA BİLGİLERİ ---
        if (testData.get("Fatura Unvani") != null) {
            sendKeysFunction( registrationPage.billingTitleInput,testData.get("Fatura Unvani"));
        }

        if (testData.get("Vergi Dairesi") != null) {
            sendKeysFunction( registrationPage.taxOfficeInput,testData.get("Vergi Dairesi"));
        }

        if (testData.get("Vergi Numarasi") != null) {
            sendKeysFunction( registrationPage.taxNumberInput,testData.get("Vergi Numarasi"));
        }

        if (testData.get("Fatura Adresi") != null) {
            sendKeysFunction( registrationPage.billingAddressInput,testData.get("Fatura Adresi"));
        }

        if (testData.get("Banka Ismi") != null) {
             registrationPage.bankNameInput.sendKeys(testData.get("Banka Ismi"));
        }

        if (testData.get("Fatura Sehri") != null) {
            sendKeysFunction( registrationPage.billingCityInput,testData.get("Fatura Sehri"));
        }

        if (testData.get("IBAN") != null) {
            sendKeysFunction( registrationPage.ibanInput,testData.get("IBAN"));
        }

        // --- YÖNETİCİ VE GİRİŞ BİLGİLERİ ---
        if (testData.get("Sirket Yetkilisi") != null) {
            sendKeysFunction( registrationPage.companyOfficialInput,testData.get("Sirket Yetkilisi"));
        }

        if (testData.get("Yonetici Cep Telefonu") != null) {
            sendKeysFunction( registrationPage.adminMobileInput,testData.get("Yonetici Cep Telefonu"));
        }

        if (testData.get("Yonetici Mail Adresi") != null) {
            sendKeysFunction( registrationPage.adminMailInput,testData.get("Yonetici Mail Adresi"));
        }

        if (testData.get("Kullanici Adi") != null) {
            sendKeysFunction( registrationPage.usernameInput,testData.get("Kullanici Adi"));
        }

        if (testData.get("Giris Mail Adresi") != null) {
            sendKeysFunction( registrationPage.loginMailInput,testData.get("Giris Mail Adresi"));
        }

        if (testData.get("Sifre") != null) {
            sendKeysFunction( registrationPage.passwordInput,testData.get("Sifre"));
        }
    }
}

