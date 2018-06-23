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

            dropDownValues = dropdown.get(indexOfLocation).getText();
            country.selectByIndex(indexOfLocation);
            String testCountryValue = ReadTestData.getTestData(indexOfLocation, 1);

            System.out.println("Actual dropdown value is " + dropDownValues);
            System.out.println("Expected country value is " + testCountryValue);
            Assert.assertEquals("Check the country value", testCountryValue, dropDownValues);


            WebElement currencyElement = driver.findElement(By.className("purchase-currency-label"));
            String testCurrencyValue = ReadTestData.getTestData(indexOfLocation, 2);
            String actualCurrencyValue = currencyElement.getText();

            System.out.println("Actual currency value is " + actualCurrencyValue);
            System.out.println("Expected currency value is " + testCurrencyValue);
            Assert.assertEquals("Check the country value", testCurrencyValue, actualCurrencyValue);

//            String testAmountValue = ReadTestData.getTestData(indexOfLocation, 3);
            WebElement purchaseAmountElement = driver.findElement(By.name("purchase_amount"));
            purchaseAmountElement.click();
            purchaseAmountElement.sendKeys("1000000");


            WebElement refundAmountElement = driver.findElement(By.className("refund-amount"));
            ExplicitWait(driver, "refund-amount");
            Assert.assertFalse(refundAmountElement.getText().isEmpty());
        }
    }


    public static void ExplicitWait(WebDriver driver, String element) {

        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className(element)));

    }

}

