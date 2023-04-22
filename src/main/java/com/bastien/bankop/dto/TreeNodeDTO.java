package com.bastien.bankop.dto;

import java.util.List;

public record TreeNodeDTO(
        String tableName,
        List<String> keywords,
        List<OperationDTO> operations,
        List<TreeNodeDTO> children
) {
}
