package com.bastien.bankop.controllers;

import com.bastien.bankop.dto.TreeTableDTO;
import com.bastien.bankop.mappers.TreeTableMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/import")
public class ImportOperationController extends AbstractEndpointController<TreeTableDTO> {

    public ImportOperationController(TreeTableMapper mapper) {
        super(mapper);
    }
}
