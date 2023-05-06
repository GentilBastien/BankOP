package com.bastien.bankop.mappers;

import com.bastien.bankop.dto.GenericDTO;
import com.bastien.bankop.entities.AbstractEntity;

public interface EntityMapper<DTO extends GenericDTO, ENT extends AbstractEntity<ID>, ID>
        extends DTOMapper<DTO, ENT> {

    ENT toEntity(DTO dto);

    DTO toDTO(ENT entity);
}
