package com.bastien.bankop.endpoints.dynamictable;

import com.bastien.bankop.controllers.OperationController;
import com.bastien.bankop.controllers.TableController;
import com.bastien.bankop.dto.DynamicTableDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/dynamic")
public class DynamicTableController {

    private final DynamicTableMapper dynamicTableMapper;

    public DynamicTableController(
            @Autowired TableController tableController,
            @Autowired OperationController operationController) {
        this.dynamicTableMapper = new DynamicTableMapper(tableController, operationController);
    }

    @PostMapping()
    public DynamicTableDTO mapDynamicTable(@RequestBody List<Integer> years) {
        return this.dynamicTableMapper.buildDynamicTableDTO(years);
    }
}
