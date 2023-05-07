package com.bastien.bankop.controllers;

import com.bastien.bankop.dto.AbstractEntityDTO;
import com.bastien.bankop.entities.AbstractEntity;
import com.bastien.bankop.services.AbstractService;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;


public class AbstractEntityController<DTO extends AbstractEntityDTO<ID>, ENT extends AbstractEntity<ID>, ID> {

    protected final AbstractService<DTO, ENT, ID> service;

    public AbstractEntityController(AbstractService<DTO, ENT, ID> service) {
        this.service = service;
    }

    @GetMapping(path = "/{id}")
    public @ResponseBody DTO get(@PathVariable ID id) {
        return this.service.toDTO(
                this.service.getEntityWithId(id)
        );
    }

    @GetMapping
    public @ResponseBody Set<DTO> getAll() {
        return this.service.getEntities()
                .stream()
                .map(this.service::toDTO)
                .collect(Collectors.toSet());
    }

    @PostMapping
    public void save(@RequestBody DTO dto) {
        this.service.saveEntity(dto);
    }

    @PutMapping
    public void update(@RequestBody DTO dto) {
        this.service.updateEntity(dto);
    }

    @DeleteMapping
    public void delete(@PathVariable ID id) {
        this.service.deleteEntity(id);
    }
}
