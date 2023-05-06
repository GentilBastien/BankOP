package com.bastien.bankop.controllers;

import com.bastien.bankop.dto.TreeTableDTO;
import com.bastien.bankop.mappers.TreeTableMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/import")
public class ImportOperationController {

    private final TreeTableMapper mapper;


    public ImportOperationController(TreeTableMapper mapper) {
        this.mapper = mapper;
    }

    @GetMapping
    public TreeTableDTO mapTreeTable() {
        return this.mapper.buildDTO();
    }
}
