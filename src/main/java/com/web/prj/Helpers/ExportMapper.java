package com.web.prj.Helpers;

@FunctionalInterface
public interface ExportMapper<T> {
    Object ExportExcelMapper(int cellIndex, T Field);
}
