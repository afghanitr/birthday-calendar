package com.afghani.birthday.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.afghani.birthday.model.Person;

public class ExcelReader {

    private static final int DATA_START_ROW = 8;

    private static final int COL_BLOCK = 2;
    private static final int COL_NAME  = 3;
    private static final int COL_NIK   = 5;

    public List<Person> read(String fileName) throws IOException {
    	List<Person> result = new ArrayList<>();
        try (Workbook workbook = new XSSFWorkbook(new FileInputStream(fileName))) {
            Sheet sheet = workbook.getSheetAt(0);
            String lastBlock = "";
            int lastRow = sheet.getLastRowNum();
            for (int rowNum = DATA_START_ROW; rowNum <= lastRow; rowNum++) {
                Row row = sheet.getRow(rowNum);

                if (row == null) {
                    continue;
                }

                String block = getCellValue(row.getCell(COL_BLOCK));
                if (!block.isBlank()) {
                    lastBlock = block;
                } else {
                    block = lastBlock;
                }
                
                String name = getCellValue(row.getCell(COL_NAME));
                String nik = getCellValue(row.getCell(COL_NIK));

                if (nik.isBlank()) {
                    continue;
                }

                result.add(new Person(name, nik, block));
            }
        }

        return result;
    }

    private String getCellValue(
            Cell cell) {

        if (cell == null) {
            return "";
        }

        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue().trim();
            case NUMERIC -> BigDecimal.valueOf(cell.getNumericCellValue()).toPlainString();
            default -> "";
        };
    }
}