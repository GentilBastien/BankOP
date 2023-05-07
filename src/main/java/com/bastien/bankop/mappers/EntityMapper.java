package com.bastien.bankop.mappers;

import com.bastien.bankop.dto.AbstractEntityDTO;
import com.bastien.bankop.entities.AbstractEntity;

public interface EntityMapper<DTO extends AbstractEntityDTO<ID>, ENT extends AbstractEntity<ID>, ID> extends DTOMapper<DTO> {

    ENT toEntity(DTO dto);

    DTO toDTO(ENT entity);
}
