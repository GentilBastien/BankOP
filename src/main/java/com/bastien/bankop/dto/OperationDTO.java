package com.bastien.bankop.dto;

import java.time.LocalDate;

public record OperationDTO(
        LocalDate date,
        String label,
        Double price,
        String tableName
) {
}
