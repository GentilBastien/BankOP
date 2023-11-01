package com.bastien.bankop.controllers;

import com.bastien.bankop.dto.ImportOperationDTO;
import com.bastien.bankop.dto.ImportRawOperationDTO;
import com.bastien.bankop.mappers.ImportOperationMapper;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/import")
public class ImportOperationController {


    private final ImportOperationMapper importOperationMapper;

    public ImportOperationController(ImportOperationMapper importOperationMapper) {
        this.importOperationMapper = importOperationMapper;
    }

    @PostMapping
    public @ResponseBody List<ImportOperationDTO> classifyNewOperations(@RequestBody List<ImportRawOperationDTO> importRawOperationDTOList) {
//        return importRawOperationDTOList.stream().map(importOperationMapper::mapTo).toList();
        return List.of(
                new ImportOperationDTO(
                        ImportOperationDTO.FILE,
                        LocalDate.of(2022, 5, 12),
                        "OPERATION MOCK 1OPERATION MOCK 1OPERATION MOCK 1",
                        -12.58,
                        "path 1"
                ),
                new ImportOperationDTO(
                        ImportOperationDTO.NONE,
                        LocalDate.of(2020, 12, 1),
                        "OPERATION MOCK 2OPERATION MOCK 2",
                        -108.01,
                        "path 1"
                ),
                new ImportOperationDTO(
                        ImportOperationDTO.DATABASE,
                        LocalDate.of(2019, 4, 19),
                        "OPERATION MOCK 3OPERATION MOCK 3OPERATION MOCK 3OPERATION MOCK 3",
                        69.00,
                        "path 2"
                )
        );
    }
}
