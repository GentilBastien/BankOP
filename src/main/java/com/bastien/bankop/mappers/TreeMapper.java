package com.bastien.bankop.mappers;

import com.bastien.bankop.dto.TreeDTO;
import com.bastien.bankop.dto.base.KeywordDTO;
import com.bastien.bankop.dto.base.OperationDTO;
import com.bastien.bankop.dto.base.TableDTO;
import com.bastien.bankop.entities.base.Table;
import com.bastien.bankop.services.base.KeywordService;
import com.bastien.bankop.services.base.OperationService;
import com.bastien.bankop.services.base.TableService;
import com.bastien.bankop.utils.BankopUtils;
import com.bastien.bankop.utils.TableID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TreeMapper implements DTOVoidMapper<TreeDTO> {

    private final TableService tableService;
    private final KeywordService keywordService;
    private final OperationService operationService;

    @Override
    public TreeDTO buildDTO() {
        Table root = this.tableService.getEntityWithId(TableID.ROOT);
        return new TreeDTO(this.buildTreeNode(root));
    }

    private TreeDTO.TreeNode buildTreeNode(Table table) {
        TableDTO tableDTO = this.tableService.toDTO(table);
        List<KeywordDTO> keywords = table.getKeywords()
                .stream()
                .map(this.keywordService::toDTO)
                .toList();
        List<OperationDTO> operations = table.getOperations()
                .stream()
                .map(this.operationService::toDTO)
                .toList();
        List<TreeDTO.TreeNode> children = table.getTables()
                .stream()
                .map(this::buildTreeNode)
                .toList();
        return new TreeDTO.TreeNode(
                tableDTO,
                BankopUtils.emptyListToNull(keywords),
                BankopUtils.emptyListToNull(operations),
                BankopUtils.emptyListToNull(children));
    }
}
