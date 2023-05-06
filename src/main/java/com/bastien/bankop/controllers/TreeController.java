package com.bastien.bankop.controllers;

import com.bastien.bankop.dto.TreeDTO;
import com.bastien.bankop.mappers.TreeMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/tree")
public class TreeController {

    private final TreeMapper mapper;

    public TreeController(TreeMapper mapper) {
        this.mapper = mapper;
    }

    @GetMapping
    public TreeDTO mapTree() {
        return this.mapper.buildDTO();
    }
}
