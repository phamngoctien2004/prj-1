package com.web.prj.Helpers;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class ExportHelper {
    public static Workbook workbook;
    public static CellStyle cellStyle;
    public static <T> ByteArrayInputStream exportExcel(List<T> items, ExportMapper<T> mapper, String[] cols) {
        try{
            workbook = new XSSFWorkbook();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            Sheet sheet = workbook.createSheet("Danh sách người dùng");

            Row header = sheet.createRow(0);

            for (int i = 0; i < cols.length; i++) {
                Cell cell = header.createCell(i);
                cell.setCellValue(cols[i]);
            }

            int startRow = 1;
            for (T item : items) {
                Row row = sheet.createRow(startRow++);
                for (int i = 0; i < cols.length; i++) {
                    Cell cell = row.createCell(i);
                    Object value = mapper.ExportExcelMapper(i, item);
                    if (value != null) {
                        formatValueToCell(value, cell);
                    }
                }
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch(Exception e){
            throw new RuntimeException();
        }
    }

    public static void formatValueToCell(Object value, Cell cell) {
        if (value instanceof String) {
            cell.setCellValue((String) value);
        } else if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Long) {
            cell.setCellValue((Long) value);
        } else if (value instanceof Double) {
            cell.setCellValue((Double) value);
        } else if (value instanceof Float) {
            cell.setCellValue(((Float) value).doubleValue());
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else if( value instanceof LocalDate){
            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setDataFormat(workbook
                    .getCreationHelper()
                    .createDataFormat()
                    .getFormat("dd/MM/yyyy"));

            Instant instant = ((LocalDate) value).atStartOfDay(ZoneId.systemDefault()).toInstant();
            Date date = Date.from(instant);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(date);
        } else if (value instanceof LocalDateTime){
            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setDataFormat(workbook
                    .getCreationHelper()
                    .createDataFormat()
                    .getFormat("dd/MM/yyyy HH:mm:ss"));
            Instant instant = ((LocalDateTime) value).atZone(ZoneId.systemDefault()).toInstant();
            Date date = Date.from(instant);
            cell.setCellValue(date);
            cell.setCellStyle(cellStyle);
        }
        else {
            // fallback
            cell.setCellValue(value.toString());
        }
    }
}
