package com.globalblue;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import java.util.List;

/**
 * Created by Yana on 4/1/2018.
 */
public class CalculatorApp {

    public void selectDDvalue() {

        WebDriver driver=new FirefoxDriver() ;
        driver.get("http://www.globalblue.com/tax-free-shopping/refund-calculator/");
        driver.manage().window().maximize();

        WebElement month_dropdown = driver.findElement(By.id("select-control"));
        Select month = new Select(month_dropdown);

        List<WebElement> dropdown = month.getOptions();

        for (int i = 0; i < dropdown.size(); i++) {

            String drop_down_values = dropdown.get(i).getText();

            System.out.println("dropdown values are " + drop_down_values);

        }
    }

}
