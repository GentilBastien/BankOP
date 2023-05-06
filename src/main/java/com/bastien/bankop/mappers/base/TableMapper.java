package com.bastien.bankop.mappers.base;

import com.bastien.bankop.dto.base.TableDTO;
import com.bastien.bankop.entities.base.Table;
import com.bastien.bankop.repositories.base.TableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TableMapper extends AbstractBaseEntityMapper<TableDTO, Table> {

    @Autowired
    public TableMapper(TableRepository tableRepository) {
        super(tableRepository, tableRepository, TableDTO.class, Table.class);
    }
}
