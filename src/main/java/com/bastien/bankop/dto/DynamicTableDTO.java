package com.bastien.bankop.dto;

import java.util.List;

public record DynamicTableDTO(List<Year> data) implements GenericDTO {
    public record Year(int year, YearTable root) {}
    public record YearTable(String tableName, String tablePath, double yearPrice, double[] monthPrices, double cumulatedYearPrice, double[] cumulatedMonthPrices, List<YearTable> children) {}
}
