package com.biletbank.utilities;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.SkipException;
import org.testng.asserts.SoftAssert;

import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

import static com.biletbank.utilities.Driver.getDriver;

public class ReusableMethods {
    static WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(15));
    private static final int DEFAULT_WAIT_TIME = 5;

    //Element sayfada yoksa test durdurulmaz devam eder rapora eklenir
    public static void verifyFaviconExists(SoftAssert softAssert) {
        List<WebElement> faviconLinks = getDriver().findElements(By.cssSelector("link[rel*='icon']"));

        if (faviconLinks.isEmpty()) {
            softAssert.fail("UI BUG: Sayfanın HTML kaynak kodunda Favicon (Sekme İkonu) bulunamadı!");
            System.out.println("Favicon bulunamadı!");
        } else {
            String faviconUrl = faviconLinks.get(0).getAttribute("href");
            try {
                HttpURLConnection connection = (HttpURLConnection) new URL(faviconUrl).openConnection();
                connection.setRequestMethod("GET");
                connection.connect();
                int responseCode = connection.getResponseCode();

                softAssert.assertEquals(responseCode, 200, "UI BUG: Favicon elementi var ancak görsel yüklenemiyor (Kırık Link)!");
                System.out.println("Link kırık : " + faviconUrl);
            } catch (Exception e) {
                softAssert.fail("Favicon URL'sine bağlanırken teknik bir hata oluştu: " + e.getMessage());
                System.out.println("teknik bir hata oluştu:");
            }
        }
        softAssert.assertAll();
    }

    // Yeni açılan sekmeye geçiş yapmayı sağlayan dinamik metot
    public static void switchToNewWindow() {
        String currentWindow = getDriver().getWindowHandle();
        Set<String> allWindows = getDriver().getWindowHandles();
        for (String windowHandle : allWindows) {
            if (!windowHandle.equals(currentWindow)) {
                getDriver().switchTo().window(windowHandle);
                break;
            }
        }
    }

    public static WebElement waitForVisibility(WebElement element, int timeout) {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    // Elementin tıklanabilir olmasını bekleyen metot (Clickable)
    public static WebElement waitForClickable(WebElement element, int timeout) {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    // Aşırı yükleme (Overloading): Zaman belirtilmezse varsayılan süreyi kullanır
    public static WebElement waitForVisibility(WebElement element) {
        return waitForVisibility(element, DEFAULT_WAIT_TIME);
    }

    public static WebElement waitForClickable(WebElement element) {
        return waitForClickable(element, DEFAULT_WAIT_TIME);
    }

    public static void selectFromDropdownByVisibleText(WebElement element, String visibleText) {

        Select dropdown = new Select(wait.until(ExpectedConditions.visibilityOf(element)));

        // Dropdown'daki tüm seçenekleri alır
        Map<String, String> options = dropdown.getOptions().stream().collect(Collectors.toMap(option -> option.getText().trim().toLowerCase(new Locale("tr")), option -> option.getText().trim(), (option1, option2) -> option1, LinkedHashMap::new));


        // Excel'den gelen veriyi uygun formata dönüştür
        String formattedText = visibleText.trim().toLowerCase(new Locale("tr"));

        if (options.containsKey(formattedText)) {
            dropdown.selectByVisibleText(options.get(formattedText));
        } else {
            throw new SkipException("Excell'e girilen data bu dropdown içerisinde mevcut değil :" + visibleText);
        }

    }

    public static void sendKeysFunction(WebElement element, String text) {

        waitUntilVisible(element);
        scrollToElement(element);
        element.clear();
        element.sendKeys(text);
    }

    public static void waitUntilVisible(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public static void scrollToElement(WebElement element) {
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView();", element);
    }

    public void selectCheckbox(WebElement... checkboxes) {
        for (WebElement checkbox : checkboxes) {
            if (!checkbox.isSelected()) {
                clickFunction(checkbox);
            }
        }
    }

    public static void clickFunction(WebElement element) {

        waitUntilClickable(element);
        scrollToElement(element);
        element.click();
    }

    public static void waitUntilClickable(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }


    public static void selectActivityAreaCheckboxes(String activityAreasFromSheet) {
        if (activityAreasFromSheet == null || activityAreasFromSheet.isEmpty()) {
            return;
        }

        // Önce tüm checkbox'ları uncheck et
        List<WebElement> allCheckboxes = Driver.getDriver().findElements(
                By.xpath("//div[div[text()='Acente Faaliyet Alanları']]//label")
        );
        for (WebElement label : allCheckboxes) {
            WebElement input = label.findElement(By.xpath(".//input"));
            if (input.isSelected()) {
                label.click();
            }
        }

        // Sadece sheet'ten gelenleri seç
        String[] activityAreas = activityAreasFromSheet.split(",");
        for (String area : activityAreas) {
            String trimmedArea = area.trim();
            String inputXpath = "//div[div[text()='Acente Faaliyet Alanları']]//label[contains(.,'" + trimmedArea + "')]//input";
            WebElement checkboxInput = Driver.getDriver().findElement(By.xpath(inputXpath));
            if (!checkboxInput.isSelected()) {
                WebElement labelElement = Driver.getDriver().findElement(
                        By.xpath("//div[div[text()='Acente Faaliyet Alanları']]//label[contains(.,'" + trimmedArea + "')]")
                );
                labelElement.click();
            }
        }
    }

}