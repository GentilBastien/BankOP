package com.bastien.bankop.dto;

import java.util.List;

public record TreeTableNodeDTO(
        TableDTO tableDTO,
        List<TreeTableNodeDTO> children
) {
}
