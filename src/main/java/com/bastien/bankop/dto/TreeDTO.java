package com.bastien.bankop.dto;

import com.bastien.bankop.dto.base.KeywordDTO;
import com.bastien.bankop.dto.base.OperationDTO;
import com.bastien.bankop.dto.base.TableDTO;

import java.util.List;

public record TreeDTO(TreeNode root) implements GenericDTO {
    public record TreeNode(TableDTO tableDTO, List<KeywordDTO> keywords, List<OperationDTO> operations, List<TreeNode> children) {}
}
