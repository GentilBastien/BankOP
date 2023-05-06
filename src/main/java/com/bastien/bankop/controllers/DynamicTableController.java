package com.bastien.bankop.controllers;

import com.bastien.bankop.dto.DynamicTableDTO;
import com.bastien.bankop.mappers.DynamicTableMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/dynamic")
public class DynamicTableController {

    private final DynamicTableMapper mapper;

    @Autowired
    public DynamicTableController(DynamicTableMapper mapper) {
        this.mapper = mapper;
    }

    @PostMapping
    public DynamicTableDTO mapDynamicTable(@RequestBody List<Integer> years) {
        return this.mapper.toDTO(years);
    }
}
