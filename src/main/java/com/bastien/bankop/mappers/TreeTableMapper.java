package com.bastien.bankop.mappers;

import com.bastien.bankop.dto.TreeTableDTO;
import com.bastien.bankop.dto.base.TableDTO;
import com.bastien.bankop.entities.base.Table;
import com.bastien.bankop.services.base.TableService;
import com.bastien.bankop.utils.BankopUtils;
import com.bastien.bankop.utils.TableID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TreeTableMapper implements DTOMapper<TreeTableDTO> {

    private final TableService service;

    @Override
    public TreeTableDTO buildDTO() {
        Table root = this.service.getEntityWithId(TableID.ROOT);
        return new TreeTableDTO(this.buildTreeTable(root));
    }

    private TreeTableDTO.TableNode buildTreeTable(Table table) {
        TableDTO tableDTO = this.service.toDTO(table);

        List<TreeTableDTO.TableNode> children = table.getTables()
                .stream()
                .map(this::buildTreeTable)
                .toList();

        return new TreeTableDTO.TableNode(
                tableDTO,
                BankopUtils.emptyListToNull(children)
        );
    }
}
