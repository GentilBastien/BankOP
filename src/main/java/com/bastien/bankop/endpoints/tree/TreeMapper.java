package com.bastien.bankop.endpoints.tree;

import com.bastien.bankop.controllers.KeywordController;
import com.bastien.bankop.controllers.OperationController;
import com.bastien.bankop.controllers.TableController;
import com.bastien.bankop.dto.OperationDTO;
import com.bastien.bankop.dto.TreeDTO;
import com.bastien.bankop.dto.TreeNodeDTO;
import com.bastien.bankop.entities.Keyword;
import com.bastien.bankop.entities.Operation;
import com.bastien.bankop.entities.Table;
import com.bastien.bankop.utils.BankopUtils;
import com.bastien.bankop.utils.TableID;
import org.springframework.beans.factory.annotation.Autowired;

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
        String tableName = this.tableController.getTable(tableId).getName();
        List<String> keywords = this.keywordController.listKeywordsFromTableId(tableId)
                .stream()
                .map(Keyword::getKeyword)
                .toList();
        List<OperationDTO> operations = this.operationController.listOperationsFromTableId(tableId)
                .stream()
                .map(Operation::getId)
                .map(id -> {
                    Operation o = this.operationController.getOperation(id);
                    return new OperationDTO(o.getDate(), o.getLabel(), o.getPrice());
                })
                .toList();
        List<TreeNodeDTO> children =
                this.tableController.listChildrenFromTableId(tableId)
                        .stream()
                        .map(Table::getId)
                        .map(this::buildTreeNode)
                        .toList();
        return new TreeNodeDTO(
                tableName,
                BankopUtils.emptyListToNull(keywords),
                BankopUtils.emptyListToNull(operations),
                BankopUtils.emptyListToNull(children));
    }
}
