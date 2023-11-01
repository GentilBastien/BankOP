package com.bastien.bankop.dto;

public record ImportRawOperationDTO(
        String date, String name, Double price
) implements GenericDTO {
}
