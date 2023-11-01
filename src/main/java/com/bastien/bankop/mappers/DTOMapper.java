package com.bastien.bankop.mappers;

import com.bastien.bankop.dto.GenericDTO;

public interface DTOMapper<DTORequest extends GenericDTO, DTOResponse extends GenericDTO> extends GenericMapper {
    DTOResponse mapTo(DTORequest dtoRequest);
}
