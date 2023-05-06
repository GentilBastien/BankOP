package com.bastien.bankop.controllers;

import com.bastien.bankop.dto.GenericDTO;
import com.bastien.bankop.mappers.GenericMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequiredArgsConstructor
public abstract class AbstractEndpointController<DTO extends GenericDTO> {

    protected final GenericMapper mapper;


    @GetMapping
    public abstract @ResponseBody DTO getDTO();
}
