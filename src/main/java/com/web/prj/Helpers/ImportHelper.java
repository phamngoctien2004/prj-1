package com.web.prj.Helpers;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ImportHelper {
    public static <T> List<T> importExcel(MultipartFile multipartFile, ImportMapper<T> importMapper) {
        List<T> items = new ArrayList<>();
        try{
            InputStream inputStream = multipartFile.getInputStream();

            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                if(row.getRowNum() != 0){
                    T item = importMapper.importExcelMapper(row);
                    System.out.println("s" + row.getRowNum());
                    items.add(item);
                }
            }
        }catch (Exception e) {
            throw new RuntimeException("Failed to parse Excel file: " + e.getMessage());
        }
        return items;
    }
}
