package com.bastien.bankop.controllers;

import com.bastien.bankop.dto.TreeDTO;
import com.bastien.bankop.mappers.TreeMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/tree")
public class TreeController extends AbstractEndpointController<TreeDTO> {

    public TreeController(TreeMapper mapper) {
        super(mapper);
    }
}
