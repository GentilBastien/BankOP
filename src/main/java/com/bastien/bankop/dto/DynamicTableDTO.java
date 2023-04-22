package com.bastien.bankop.dto;

import java.util.List;

public record DynamicTableDTO(
        List<TableYearDetailDTO> data
) {
}