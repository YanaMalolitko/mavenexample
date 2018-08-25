package com.testpetstore;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;


/**
 * Created by Yana on 4/2/2018.
 */

public class ReadTestData {


    public static String getTestData(int indexOfLocation, int objectLocation) throws IOException {

        File FileName = new File("C:\\Users\\Yana\\mavenexample\\testData\\testDataGlobalBlue.xlsx");
        FileInputStream file = new FileInputStream(new File(String.valueOf(FileName)));
        Workbook workbook = new XSSFWorkbook(file);
        XSSFSheet sheet1 = (XSSFSheet) workbook.getSheetAt(0);
        String testedObjectValue = sheet1.getRow(indexOfLocation).getCell(objectLocation).getStringCellValue();

        return testedObjectValue;

    }




//    public static String getCountryValue(int indexOfLocation) {
//
//
//        return countryValue (indexOfLocation);
//    }
//
//    public static String getCurrencyValue(int indexOfLocation) {
//        return currencyValue;
//    }
}
