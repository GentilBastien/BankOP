package com.bastien.bankop.mappers;

import com.bastien.bankop.controllers.TableController;
import com.bastien.bankop.dto.TableDTO;
import com.bastien.bankop.dto.TreeTableDTO;
import com.bastien.bankop.dto.TreeTableNodeDTO;
import com.bastien.bankop.entities.Table;
import com.bastien.bankop.utils.BankopUtils;
import com.bastien.bankop.utils.TableID;

import java.util.List;

public class TreeTableMapper {

    private final TableController tableController;

    public TreeTableMapper(TableController tableController) {
        this.tableController = tableController;
    }

    public TreeTableDTO buildTreeTableDTO() {
        return new TreeTableDTO(this.buildTreeTableNodeDTO(TableID.ROOT));
    }

    private TreeTableNodeDTO buildTreeTableNodeDTO(Long tableId) {
        TableDTO tableDTO = this.tableController.mapToDTO(tableId);
        List<TreeTableNodeDTO> children = this.tableController.listChildrenFromTableId(tableId)
                .stream()
                .map(Table::getId)
                .map(this::buildTreeTableNodeDTO)
                .toList();

        return new TreeTableNodeDTO(
                tableDTO,
                BankopUtils.emptyListToNull(children)
        );
    }
}
