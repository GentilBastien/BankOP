package com.bastien.bankop.dto;

import java.time.LocalDate;

public record ImportOperationDTO(
        String doublon,
        LocalDate date,
        String name,
        Double price,
        String path
) implements GenericDTO {
    public static String NONE = "none";
    public static String FILE = "file";
    public static String DATABASE = "releve";
}
