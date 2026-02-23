package com.biletbank.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        // Raporlama eklentileri (Plugins)
        plugin = {
                "pretty",
                "html:target/cucumber-reports/cucumber.html",
                "json:target/cucumber-reports/cucumber.json",
                "junit:target/cucumber-reports/cucumber.xml",
                "rerun:target/failed_scenarios.txt"
        },

        features = "src/test/resources/features",
        glue = {"com.biletbank.stepdefinitions", "com.biletbank.hooks"},
        tags = "@registration",
        dryRun = false,
        monochrome = true
)
public class Runner extends AbstractTestNGCucumberTests {
}
