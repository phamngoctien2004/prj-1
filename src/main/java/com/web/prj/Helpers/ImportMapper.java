package com.web.prj.Helpers;

import org.apache.poi.ss.usermodel.Row;

import java.util.List;

@FunctionalInterface
public interface ImportMapper<T> {
    T importExcelMapper(Row row);
}
