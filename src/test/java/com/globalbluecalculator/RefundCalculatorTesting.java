package com.globalbluecalculator;

import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
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
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.valueOf;


/**
 * Created by Yana on 4/1/2018.
 */
public class RefundCalculatorTesting {

    public static String dropDownValues;
    static WebDriver driver;
    static WebElement countryDropdown;
    static List<WebElement> dropDownList;
    static Select country;


    @Given("^Open the Firefox and launch the application$")
    public void open_the_Firefox_and_launch_the_application() throws Exception {
        System.setProperty("webdriver.gecko.driver", "C:\\Users\\Yana\\Java\\geckodriver.exe");
        driver = new FirefoxDriver();
        driver.get("http://www.globalblue.com/tax-free-shopping/refund-calculator/");
        driver.manage().window().maximize();
    }

    @Test
    @When("^I click on countries dropdown list$")
    public void i_click_on_countries_dropdown_list() throws Exception {
        countryDropdown = driver.findElement(By.name("jurisdictions"));
    }


    @And("^it is displayed$")
    public void itIsDisplayed() throws Throwable {

        Assert.assertTrue(countryDropdown.isDisplayed());
    }


    @Then("^I verifying that number of countries from the list equal (\\d+)$")
    public void iVerifyingThatNumberOfCountriesFromTheListEqual(int expectedNumber) throws Exception {
        country = new Select(countryDropdown);
        dropDownList = country.getOptions();
        int dropdownCount = dropDownList.size();
        //Dropdown list contains title what should to be excluded from result -1
        Assert.assertEquals(dropdownCount - 1, expectedNumber);
        System.out.println("Total Number of item count in dropdown list = " + (dropdownCount - 1));
    }


    @And("^each country is spelled correctly$")
    public void eachCountryIsSpelledCorrectly() throws Throwable {

        for (int indexOfLocation = 1; indexOfLocation < dropDownList.size(); indexOfLocation++) {

            //Implementation of TS_GlobalBlue_001. Test Case TC_RefCalc_002
            //Check the country name values in Drop Down list.
            //Compare country name values from the list with scope of countries covered by refund system

            dropDownValues = dropDownList.get(indexOfLocation).getText();
            country.selectByIndex(indexOfLocation);
            String testCountryValue = ReadTestData.getTestData(indexOfLocation, 1);

            Assert.assertEquals("Check the country value", testCountryValue, dropDownValues);

        }

    }

    @And("^currency with initial calculator elements states are correct$")
    public void currencyWithInitialCalculatorElementsStatesAreCorrect() throws Throwable {

        for (int indexOfLocation = 1; indexOfLocation < dropDownList.size(); indexOfLocation++) {

            //Select country name values from Drop Down list
            //1. Currency code is visible and equal to required
            dropDownValues = dropDownList.get(indexOfLocation).getText();
            country.selectByIndex(indexOfLocation);
            WebElement currencyElement = driver.findElement(By.className("purchase-currency-label"));
            String expectedCurrencyValue = ReadTestData.getTestData(indexOfLocation, 2);
            String actualCurrencyValue = currencyElement.getText();
            Assert.assertEquals(expectedCurrencyValue, actualCurrencyValue);

            //2. Currency symbol is visible and equal to required
            WebElement currencySymbolElement = driver.findElement(By.className("purchase-currency-symbol"));
            String expectedCurrencySymbol = ReadTestData.getTestData(indexOfLocation, 3);
            String actualCurrencySymbol = currencySymbolElement.getText();
            Assert.assertEquals(expectedCurrencySymbol, actualCurrencySymbol);

            //3. Purchase amount text box is empty.
            WebElement purchaseAmount = driver.findElement(By.className("purchase-amount"));
            purchaseAmount.getText().isEmpty();

            //4. "Total Purchase" is equal 0.
            WebElement totalPurchaseAmount = driver.findElement(By.className("total-purchase-amount"));
            totalPurchaseAmount.getText().equals(0);

            //5. "Refund" value is empty.
            WebElement refundValue = driver.findElement(By.className("refund-amount"));
            refundValue.getText().isEmpty();

            //6. "Total Refund" is equal 0.
            WebElement totalRefundValue = driver.findElement(By.className("total-refund-amount"));
            totalRefundValue.getText().equals(0);

            //7. Button "Add Purchase" is invisible.
            WebElement addPurchase = driver.findElement(By.className("add-purchase"));
            addPurchase.isEnabled();

            //8. Button "Refresh" is invisible.
            WebElement buttonRefresh = driver.findElement(By.className("btn-reset"));
            Assert.assertFalse(buttonRefresh.isDisplayed());


        }
    }

    @And("^check refund calculation with min amount$")
    public void checkRefundCalculationWithMinAmount() throws Exception {
        country = new Select(countryDropdown);
        dropDownList = country.getOptions();


        for (int indexOfLocation = 1; indexOfLocation < dropDownList.size(); indexOfLocation++) {


            dropDownValues = dropDownList.get(indexOfLocation).getText();
            country.selectByIndex(indexOfLocation);
            DecimalFormat df = new DecimalFormat("#.##");

            WebElement purchaseAmountElement = driver.findElement(By.name("purchase_amount"));
            purchaseAmountElement.click();
            String testMinAmountValue = ReadTestData.getTestData(indexOfLocation, 4);
            purchaseAmountElement.sendKeys(testMinAmountValue);

            WebElement refundAmountElement = driver.findElement(By.className("refund-amount"));
            ExplicitWait(driver, "refund-amount");

            double finalRefund = Double.parseDouble(refundAmountElement.getText().replace(",", ""));

            //1. "Total Purchase" is equal to entered amount
            WebElement totalPurchaseAmountElement = driver.findElement(By.className("total-purchase-amount"));
            double finalPurchaseAmount = Double.parseDouble(totalPurchaseAmountElement.getText().replace(",", ""));

            double minAmount = Double.parseDouble(testMinAmountValue.replace(",", ""));
            ExplicitWait(driver, "total-purchase-amount");
            Assert.assertEquals(df.format(minAmount), df.format(finalPurchaseAmount));

            //2. "Refund" value is calculated due to specified  %
            String refundPercentage = ReadTestData.getTestData(indexOfLocation, 6);
            double expectedRefund = (Double.parseDouble(refundPercentage) / 100) * minAmount;
            Assert.assertEquals(df.format(expectedRefund), df.format(finalRefund));

            //3. "Total Refund" value is equal to "Refund"
            WebElement totalRefundAmountElement = driver.findElement(By.className("total-refund-amount"));
            double finalRefundAmount = Double.parseDouble(totalRefundAmountElement.getText().replace(",", ""));

            Assert.assertEquals(df.format(finalRefund), df.format(finalRefundAmount));

            //4. Button "Add Purchase" is active
            WebElement addPurchase = driver.findElement(By.className("add-purchase"));
            Assert.assertTrue(addPurchase.isEnabled());

            //5. Button "Refresh" is active
            WebElement buttonRefresh = driver.findElement(By.className("btn-reset"));
            Assert.assertTrue(buttonRefresh.isDisplayed());

        }
    }

    public static void ExplicitWait(WebDriver driver, String element) {

        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className(element)));

    }



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

    public void toTest() throws Throwable {

        System.setProperty("webdriver.gecko.driver", "C:\\Users\\Yana\\Java\\geckodriver.exe");
        driver = new FirefoxDriver();
        driver.get("http://www.globalblue.com/tax-free-shopping/refund-calculator/");
        driver.manage().window().maximize();


        WebElement countryDropdown = driver.findElement(By.name("jurisdictions"));
        Select country = new Select(countryDropdown);
        List<WebElement> dropdown = country.getOptions();


        for (int indexOfLocation = 1; indexOfLocation < dropdown.size(); indexOfLocation++) {
            String dropDownValues1 = dropdown.get(indexOfLocation).getText();
            country.selectByIndex(indexOfLocation);


            WebElement purchaseAmountElement = driver.findElement(By.name("purchase_amount"));
            purchaseAmountElement.click();
            String testMinAmountValue = ReadTestData.getTestData(indexOfLocation, 4);
            purchaseAmountElement.sendKeys(testMinAmountValue);

            WebElement refundAmountElement = driver.findElement(By.className("refund-amount"));
            ExplicitWait(driver, "refund-amount");
            String refundValue = refundAmountElement.getText();
            boolean a = refundValue.contains(",");
            String finalRefund = null;

            if (a = true) {
                finalRefund = refundValue.replace(",", "");
            } else {
                finalRefund = refundValue;
            }

//            Assert.assertFalse(refundAmountElement.getText().isEmpty());
//            System.out.println("Actual purchase Amount Element value is " + testMinAmountValue);
//            System.out.println("Expected refund Amount Element value is " + finalRefund);
            float refundAmount = Float.parseFloat((finalRefund));

//            System.out.println( ("Refund % :" +refundAmount/(Float.parseFloat(testMinAmountValue)/100)));
            System.out.println((refundAmount / (Float.parseFloat(testMinAmountValue) / 100)));
        }

    }


}

