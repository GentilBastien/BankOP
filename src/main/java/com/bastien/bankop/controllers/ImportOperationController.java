package com.bastien.bankop.controllers;

import com.bastien.bankop.dto.RequestImportOperationDTO;
import com.bastien.bankop.dto.ResponseImportOperationDTO;
import com.bastien.bankop.mappers.ImportOperationMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/import")
public class ImportOperationController {


    private final ImportOperationMapper importOperationMapper;

    public ImportOperationController(ImportOperationMapper importOperationMapper) {
        this.importOperationMapper = importOperationMapper;
    }

    @PostMapping
    public @ResponseBody List<ResponseImportOperationDTO> classifyNewOperations(@RequestBody List<RequestImportOperationDTO> requestImportOperationDTOList) {
        return requestImportOperationDTOList.stream().map(importOperationMapper::mapTo).toList();
//        return List.of(
//                new ResponseImportOperationDTO(
//                        ResponseImportOperationDTO.FILE,
//                        LocalDate.of(2022, 5, 12),
//                        "OPERATION MOCK 1OPERATION MOCK 1OPERATION MOCK 1",
//                        -12.58,
//                        "path 1"
//                ),
//                new ResponseImportOperationDTO(
//                        ResponseImportOperationDTO.NONE,
//                        LocalDate.of(2020, 12, 1),
//                        "OPERATION MOCK 2OPERATION MOCK 2",
//                        -108.01,
//                        "path 1"
//                ),
//                new ResponseImportOperationDTO(
//                        ResponseImportOperationDTO.DATABASE,
//                        LocalDate.of(2019, 4, 19),
//                        "OPERATION MOCK 3OPERATION MOCK 3OPERATION MOCK 3OPERATION MOCK 3",
//                        69.00,
//                        "path 2"
//                )
//        );
    }
}
