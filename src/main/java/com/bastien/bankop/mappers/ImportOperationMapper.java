package com.bastien.bankop.mappers;

import com.bastien.bankop.dto.GenericDTO;
import com.bastien.bankop.dto.RequestImportOperationDTO;
import com.bastien.bankop.dto.ResponseImportOperationDTO;
import com.bastien.bankop.services.base.KeywordService;
import com.bastien.bankop.services.base.TableService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class ImportOperationMapper implements DTOMapper<RequestImportOperationDTO, ResponseImportOperationDTO> {

    private final KeywordService keywordService;
    private final TableService tableService;

    @Override
    public ResponseImportOperationDTO mapTo(RequestImportOperationDTO requestImportOperationDTO) {
        LocalDate date = LocalDate.parse(requestImportOperationDTO.date(), GenericDTO.DATE_TIME_FORMATTER);
        String name = requestImportOperationDTO.name();
        Double price = requestImportOperationDTO.price();
        Long categoryId = this.keywordService.classifyLabelWithKeyword(requestImportOperationDTO.name());
        String categoryName = tableService.getEntityWithId(categoryId).getName();
        return new ResponseImportOperationDTO(ResponseImportOperationDTO.NONE, date, name, price, categoryId, categoryName);
    }
}
