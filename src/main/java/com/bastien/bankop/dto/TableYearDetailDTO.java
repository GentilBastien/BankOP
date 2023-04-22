package com.bastien.bankop.dto;

public record TableYearDetailDTO(
        int year,
        TableNodeYearDetailDTO root
) {
}