package com.bastien.bankop.dto;

import java.util.List;

public record TreeNodeDTO(
        TableDTO tableDTO,
        List<KeywordDTO> keywords,
        List<OperationDTO> operations,
        List<TreeNodeDTO> children
) {
}
