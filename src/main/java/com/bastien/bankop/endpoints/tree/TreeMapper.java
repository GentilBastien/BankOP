package com.bastien.bankop.endpoints.tree;

import com.bastien.bankop.controllers.KeywordController;
import com.bastien.bankop.controllers.OperationController;
import com.bastien.bankop.controllers.TableController;
import com.bastien.bankop.dto.*;
import com.bastien.bankop.entities.Keyword;
import com.bastien.bankop.entities.Operation;
import com.bastien.bankop.entities.Table;
import com.bastien.bankop.utils.BankopUtils;
import com.bastien.bankop.utils.TableID;

import java.util.List;

public class TreeMapper {

    private final TableController tableController;

    private final KeywordController keywordController;

    private final OperationController operationController;

    public TreeMapper(
            TableController tableController,
            KeywordController keywordController,
            OperationController operationController) {
        this.tableController = tableController;
        this.keywordController = keywordController;
        this.operationController = operationController;
    }

    public TreeDTO buildTreeDTO() {
        return new TreeDTO(this.buildTreeNode(TableID.ROOT));
    }

    private TreeNodeDTO buildTreeNode(Long tableId) {
        TableDTO tableDTO = this.tableController.mapToDTO(tableId);

        List<KeywordDTO> keywords = this.keywordController.listKeywordsFromTableId(tableId)
                .stream()
                .map(Keyword::getKeyword)
                .map(this.keywordController::mapToDTO)
                .toList();
        List<OperationDTO> operations = this.operationController.listOperationsFromTableId(tableId)
                .stream()
                .map(Operation::getId)
                .map(this.operationController::mapToDTO)
                .toList();
        List<TreeNodeDTO> children =
                this.tableController.listChildrenFromTableId(tableId)
                        .stream()
                        .map(Table::getId)
                        .map(this::buildTreeNode)
                        .toList();
        return new TreeNodeDTO(
                tableDTO,
                BankopUtils.emptyListToNull(keywords),
                BankopUtils.emptyListToNull(operations),
                BankopUtils.emptyListToNull(children));
    }
}
