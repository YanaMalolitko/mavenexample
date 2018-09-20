package com.globalbluecalculator;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.util.List;


/**
 * Created by Yana on 4/1/2018.
 */
public class RefundCalculatorTesting {

    public static String dropDownValues;
    static WebDriver driver;

    @Test
    public void selectDropDownValue() throws IOException, InterruptedException {
        System.setProperty("webdriver.gecko.driver", "C:\\Users\\Yana\\Java\\geckodriver.exe");
        driver = new FirefoxDriver();
        driver.get("http://www.globalblue.com/tax-free-shopping/refund-calculator/");
        driver.manage().window().maximize();


        WebElement countryDropdown = driver.findElement(By.name("jurisdictions"));
        Select country = new Select(countryDropdown);
        List<WebElement> dropdown = country.getOptions();


        for (int indexOfLocation = 1; indexOfLocation < dropdown.size(); indexOfLocation++) {

            //Implementation of TS_GlobalBlue_001. Test Case TC_RefCalc_002
            //Check the country name values in Drop Down list.
            //Compare country name values from the list with scope of countries covered by refund system

            dropDownValues = dropdown.get(indexOfLocation).getText();
            country.selectByIndex(indexOfLocation);
            String testCountryValue = ReadTestData.getTestData(indexOfLocation, 1);

            System.out.println("Actual dropdown value is " + dropDownValues);
            System.out.println("Expected country value is " + testCountryValue);
            Assert.assertEquals("Check the country value", testCountryValue, dropDownValues);


            //Implementation of TS_GlobalBlue_001. Test Case TC_RefCalc_003
            //Select country name values from Drop Down list
            //Currency code is visible and equal to required.

            WebElement currencyElement = driver.findElement(By.className("purchase-currency-label"));
            String testCurrencyValue = ReadTestData.getTestData(indexOfLocation, 2);
            String actualCurrencyValue = currencyElement.getText();

            System.out.println("Actual currency value is " + actualCurrencyValue);
            System.out.println("Expected currency value is " + testCurrencyValue);
            Assert.assertEquals("Check the country value", testCurrencyValue, actualCurrencyValue);


            //Implementation of TS_GlobalBlue_001. Test Case TC_RefCalc_003
            //Enter min amount for refund
            // Refund" value is calculated

            WebElement purchaseAmountElement = driver.findElement(By.name("purchase_amount"));
            purchaseAmountElement.click();
            String testMinAmountValue = ReadTestData.getTestData(indexOfLocation, 4);
            purchaseAmountElement.sendKeys(testMinAmountValue);

            WebElement refundAmountElement = driver.findElement(By.className("refund-amount"));
            ExplicitWait(driver, "refund-amount");

            Assert.assertFalse(refundAmountElement.getText().isEmpty());
            System.out.println("Actual purchase Amount Element value is " + testMinAmountValue);
            System.out.println("Expected refund Amount Element value is " + refundAmountElement.getText());

        }
    }


    public static void ExplicitWait(WebDriver driver, String element) {

        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className(element)));

    }

}

