package com.bastien.bankop.dto;

import java.time.LocalDate;
import java.util.List;

public record ReleveOperationDTO(
        List<ReleveRow> rows,
        LocalDate minDate,
        LocalDate maxDate,
        Double minPrice,
        Double maxPrice,
        String[] categories
) implements GenericDTO {
    public record ReleveRow(LocalDate date, String name, Double price, String path) {
    }
}
