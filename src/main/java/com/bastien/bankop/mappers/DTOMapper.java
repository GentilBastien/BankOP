package com.bastien.bankop.mappers;

import com.bastien.bankop.dto.GenericDTO;

public interface DTOMapper<DTO extends GenericDTO> extends GenericMapper {
    DTO buildDTO();
}
