package com.bastien.bankop.mappers;

import com.bastien.bankop.dto.ImportOperationDTO;
import com.bastien.bankop.dto.ImportRawOperationDTO;
import org.springframework.stereotype.Component;

@Component
public class ImportOperationMapper implements DTOMapper<ImportRawOperationDTO, ImportOperationDTO> {
    @Override
    public ImportOperationDTO mapTo(ImportRawOperationDTO importRawOperationDTO) {
        return null;
    }
}
