package com.bastien.bankop.dto;

import java.util.List;

public record DynamicTableDTO(TreePriceTable data) implements GenericDTO {
    public record TreePriceTable(PriceTable root, int[] years) {
    }

    public record PriceTable(String tableName, int depth, double[] yearPrices,
                             double[][] monthYearPrices, double[] cumulatedYearPrices,
                             double[][] cumulatedMonthYearPrices, List<PriceTable> children) {
    }
}
