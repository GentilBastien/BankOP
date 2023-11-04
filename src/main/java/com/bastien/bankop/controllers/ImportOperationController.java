package com.bastien.bankop.controllers;

import com.bastien.bankop.dto.RequestImportOperationDTO;
import com.bastien.bankop.dto.ResponseImportOperationDTO;
import com.bastien.bankop.mappers.ImportOperationMapper;
import com.bastien.bankop.services.base.OperationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/import")
public class ImportOperationController {

    private final ImportOperationMapper importOperationMapper;
    private final OperationService operationService;

    public ImportOperationController(ImportOperationMapper importOperationMapper, OperationService operationService) {
        this.importOperationMapper = importOperationMapper;
        this.operationService = operationService;
    }

    @PostMapping
    public @ResponseBody List<ResponseImportOperationDTO> classifyNewOperations(@RequestBody List<RequestImportOperationDTO> requestImportOperationDTOList) {
        List<ResponseImportOperationDTO> responseImportOperationDTOList = requestImportOperationDTOList.stream()
                .map(importOperationMapper::mapTo).toList();
        setDoublonsInFile(responseImportOperationDTOList);
        this.operationService.setDoublonsInDatabase(responseImportOperationDTOList);
        return responseImportOperationDTOList;
    }

    private void setDoublonsInFile(List<ResponseImportOperationDTO> list) {
        for (ResponseImportOperationDTO result : list) {
            list.stream()
                    .filter(a -> a != result && a.equals(result))
                    .forEach(e -> e.setDoublon(ResponseImportOperationDTO.FILE));
        }
    }
}
