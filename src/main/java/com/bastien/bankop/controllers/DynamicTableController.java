package com.bastien.bankop.controllers;

import com.bastien.bankop.dto.DynamicTableDTO;
import com.bastien.bankop.mappers.DynamicTableMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/dynamic")
public class DynamicTableController extends AbstractEndpointController<DynamicTableDTO> {

    public DynamicTableController(DynamicTableMapper mapper) {
        super(mapper);
    }
}
