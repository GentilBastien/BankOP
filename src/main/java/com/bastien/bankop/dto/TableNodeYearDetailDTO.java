package com.bastien.bankop.dto;

import java.util.List;

public record TableNodeYearDetailDTO(
        String tableName,
        String tablePath,
        double yearPrice,
        double[] monthPrices,
        double cumulatedYearPrice,
        double[] cumulatedMonthPrices,
        List<TableNodeYearDetailDTO> children
) {
}