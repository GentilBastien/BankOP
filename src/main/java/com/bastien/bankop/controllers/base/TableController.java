package com.bastien.bankop.controllers.base;

import com.bastien.bankop.dto.base.TableDTO;
import com.bastien.bankop.entities.base.Table;
import com.bastien.bankop.services.base.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/tables")
public class TableController extends AbstractBaseEntityController<TableDTO, Table> {

    @Autowired
    public TableController(TableService service) {
        super(service);
    }
}
