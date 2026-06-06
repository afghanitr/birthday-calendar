package com.afghani.birthday.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.afghani.birthday.model.Person;

public class BirthdayExcelWriter {

    private static final String[] MONTH_NAMES = {"", "Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus", "September", "Oktober", "November", "Desember"};
    private static final int[] DAYS_IN_MONTH = {0, 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    public void write(Map<Integer, Map<Integer, List<Person>>> birthdaysByMonth, String fileName) throws IOException {
        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            CellStyle wrapStyle = workbook.createCellStyle();
            wrapStyle.setWrapText(true);
            wrapStyle.setVerticalAlignment(VerticalAlignment.TOP);

            for (int month = 1; month <= 12; month++) {
                XSSFSheet sheet = workbook.createSheet(MONTH_NAMES[month]);

                CellStyle titleStyle = workbook.createCellStyle();
                titleStyle.setAlignment(HorizontalAlignment.CENTER);
                Font titleFont = workbook.createFont();
                titleFont.setBold(true);
                titleFont.setFontHeightInPoints((short) 14);
                titleStyle.setFont(titleFont);
                
                createMonthSheet(sheet, wrapStyle, titleStyle, birthdaysByMonth.get(month), month);
            }

            try (FileOutputStream fos = new FileOutputStream(fileName)) {
                workbook.write(fos);
            }
        }
    }

    private void createMonthSheet(XSSFSheet sheet, CellStyle wrapStyle, CellStyle titleStyle, Map<Integer, List<Person>> birthdaysByDay, int month) {
        Row titleRow = sheet.createRow(0);
        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue(MONTH_NAMES[month]);
        titleCell.setCellStyle(titleStyle);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 6));
        
        int currentRow = 2;
        int daysInMonth = DAYS_IN_MONTH[month];

        for (int startDay = 1; startDay <= daysInMonth; startDay += 7) {

            Row headerRow = sheet.createRow(currentRow++);
            Row dataRow = sheet.createRow(currentRow++);

            for (int offset = 0; offset < 7; offset++) {

                int day = startDay + offset;

                if (day > daysInMonth) {
                    break;
                }

                Cell headerCell = headerRow.createCell(offset);
                headerCell.setCellValue(day);

                String content = buildContent(birthdaysByDay == null ? null : birthdaysByDay.get(day));

                Cell dataCell = dataRow.createCell(offset);
                dataCell.setCellValue(content);
                dataCell.setCellStyle(wrapStyle);
            }

            dataRow.setHeightInPoints(100);

            currentRow++;
        }

        for (int col = 0; col < 7; col++) {
            sheet.setColumnWidth(col, 8000);
        }
    }

    private String buildContent(List<Person> persons) {
        if (persons == null || persons.isEmpty()) {
            return "";
        }

        StringBuilder sb = new StringBuilder();

        for (Person person : persons) {
            if (!sb.isEmpty()) {
                sb.append("\n");
            }

            sb.append(person.getName())
              .append(" (")
              .append(person.getBlock())
              .append(")");
        }

        return sb.toString();
    }
}