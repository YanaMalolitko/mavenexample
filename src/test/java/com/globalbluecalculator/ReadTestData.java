package com.globalbluecalculator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static org.apache.poi.ss.usermodel.CellType.STRING;


/**
 * Created by Yana on 4/2/2018.
 */

public class ReadTestData {


    public static String getTestData(int indexOfLocation, int objectLocation) throws IOException {

        File FileName = new File("testData/testDataGlobalBlue.xlsx");
        FileInputStream file = new FileInputStream(new File(String.valueOf(FileName)));
        Workbook workbook = new XSSFWorkbook(file);
        XSSFSheet sheet1 = (XSSFSheet) workbook.getSheetAt(0);

        CellType cellType = sheet1.getRow(indexOfLocation).getCell(objectLocation).getCellTypeEnum();
        String testedObjectValue = null;

        switch (cellType) {
            case NUMERIC:
                testedObjectValue = String.valueOf(sheet1.getRow(indexOfLocation).getCell(objectLocation).getNumericCellValue());
                break;

            case STRING:

                testedObjectValue = sheet1.getRow(indexOfLocation).getCell(objectLocation).getStringCellValue();

                break;
        }


        return testedObjectValue;

    }
    
}
