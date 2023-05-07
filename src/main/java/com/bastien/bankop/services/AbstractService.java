package com.bastien.bankop.services;

import com.bastien.bankop.dto.AbstractEntityDTO;
import com.bastien.bankop.entities.AbstractEntity;
import com.bastien.bankop.exceptions.MalFormedDTOException;
import com.bastien.bankop.exceptions.MalFormedEntityException;
import com.bastien.bankop.exceptions.NoEntityFoundException;
import com.bastien.bankop.mappers.EntityMapper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class AbstractService<DTO extends AbstractEntityDTO<ID>, ENT extends AbstractEntity<ID>, ID> {

    protected final JpaRepository<ENT, ID> repository;

    protected final EntityMapper<DTO, ENT, ID> mapper;

    protected AbstractService(
            JpaRepository<ENT, ID> repository,
            EntityMapper<DTO, ENT, ID> mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    protected abstract void validateEntity(ENT entity) throws MalFormedEntityException;

    protected void validateDTOBeforeSave(DTO dto) throws MalFormedDTOException {
        dto.getId().ifPresent(id -> {
            throw new MalFormedDTOException("id " + id + " should not be set in the dto for a save method.");
        });
    }

    protected void validateDTOBeforeUpdate(DTO dto) throws MalFormedDTOException {
        ID id = dto.getId().orElseThrow(() -> new MalFormedDTOException("dto does not contain a id."));
        if (!this.repository.existsById(id)) // check if update is being done on an existing id
            throw new MalFormedDTOException("id does not exist.");
    }

    protected abstract void validateEntityBeforeDelete(ENT entity) throws IllegalStateException;

    public DTO toDTO(ENT entity) {
        return this.mapper.toDTO(entity);
    }

    public ENT toEntity(DTO dto) {
        return this.mapper.toEntity(dto);
    }

    public ENT getEntityWithId(ID id) {
        return this.repository.findById(id).orElseThrow(NoEntityFoundException::new);
    }

    public Set<ENT> getEntities() {
        return new HashSet<>(this.repository.findAll());
    }

    public void saveEntity(DTO dto) {
        this.validateDTOBeforeSave(dto);
        ENT entity = this.toEntity(dto);
        this.validateEntity(entity);
        this.repository.save(entity);
    }

    public void updateEntity(DTO dto) {
        this.validateDTOBeforeUpdate(dto);
        ENT entity = this.toEntity(dto);
        this.validateEntity(entity);
        this.repository.save(entity);
    }

    public void deleteEntity(ID id) {
        this.validateEntityBeforeDelete(this.getEntityWithId(id));
        this.repository.deleteById(id);
    }
}
