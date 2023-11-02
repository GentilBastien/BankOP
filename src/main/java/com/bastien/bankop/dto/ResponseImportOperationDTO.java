package com.bastien.bankop.dto;

import java.time.LocalDate;

public record ResponseImportOperationDTO(
        String doublon,
        LocalDate date,
        String name,
        Double price,
        Long categoryId,
        String categoryName
) implements GenericDTO {
    public static String NONE = "none";
    public static String FILE = "file";
    public static String DATABASE = "releve";
}
