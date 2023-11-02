package com.bastien.bankop.dto;

public record RequestImportOperationDTO(
        String date, String name, Double price
) implements GenericDTO {
}
