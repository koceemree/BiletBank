package com.biletbank.hooks;

import com.biletbank.utilities.Driver;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.time.Duration;

public class Hooks {

    @Before
    public void setUp(Scenario scenario) {
        // Her senaryo öncesi çalışır
        System.out.println("Senaryo Başlıyor: " + scenario.getName());
        Driver.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        Driver.getDriver().manage().window().maximize();
    }

    @After
    public void tearDown(Scenario scenario) {
        // Eğer senaryo başarısız olursa (fail), ekran görüntüsü alıp rapora ekler
        if (scenario.isFailed()) {
            final byte[] screenshot = ((TakesScreenshot) Driver.getDriver()).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", "Hata_Ekrani");
        }

        // Her senaryo bittiğinde tarayıcıyı güvenli bir şekilde kapatır
        Driver.closeDriver();
    }
}