package com.bastien.bankop.dto;

import com.bastien.bankop.dto.base.TableDTO;

import java.util.List;

public record TreeTableDTO(TableNode root) implements GenericDTO {
    public record TableNode(TableDTO tableDTO, List<TableNode> children) {}
}
