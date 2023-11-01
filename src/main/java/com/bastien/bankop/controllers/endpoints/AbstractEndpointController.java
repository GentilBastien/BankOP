package com.bastien.bankop.controllers.endpoints;

import com.bastien.bankop.dto.GenericDTO;
import com.bastien.bankop.mappers.DTOBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

public abstract class AbstractEndpointController<DTO extends GenericDTO> {

    protected final DTOBuilder<DTO> mapper;

    protected AbstractEndpointController(DTOBuilder<DTO> mapper) {
        this.mapper = mapper;
    }

    @GetMapping
    public @ResponseBody DTO getDTO() {
        return mapper.buildDTO();
    }
}
