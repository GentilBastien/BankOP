package com.bastien.bankop.dto;

import java.time.LocalDate;

public record ReleveOperationDTO(
        ReleveRow[] rows,
        LocalDate minDate,
        LocalDate maxDate,
        Double minPrice,
        Double maxPrice,
        String[] categories
) implements GenericDTO {
    public record ReleveRow(LocalDate date, String name, Double price, String path) {
    }
}
