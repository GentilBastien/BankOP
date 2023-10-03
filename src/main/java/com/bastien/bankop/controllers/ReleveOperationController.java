package com.bastien.bankop.controllers;

import com.bastien.bankop.dto.ReleveOperationDTO;
import com.bastien.bankop.mappers.ReleveOperationMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/releve")
public class ReleveOperationController extends AbstractEndpointController<ReleveOperationDTO> {

    public ReleveOperationController(ReleveOperationMapper mapper) {
        super(mapper);
    }
}
