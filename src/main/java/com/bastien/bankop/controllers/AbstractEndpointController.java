package com.bastien.bankop.controllers;

import com.bastien.bankop.dto.GenericDTO;
import com.bastien.bankop.mappers.DTOMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

public abstract class AbstractEndpointController<DTO extends GenericDTO> {

    protected final DTOMapper<DTO> mapper;

    protected AbstractEndpointController(DTOMapper<DTO> mapper) {
        this.mapper = mapper;
    }


    @GetMapping
    public @ResponseBody DTO getDTO() {
        return mapper.buildDTO();
    }
}
