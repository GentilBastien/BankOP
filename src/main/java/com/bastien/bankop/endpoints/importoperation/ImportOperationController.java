package com.bastien.bankop.endpoints.importoperation;

import com.bastien.bankop.dto.OperationDTO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/import")
public class ImportOperationController {

    public ImportOperationController() {

    }

    public void importOperationDTO(List<OperationDTO> operationDTOList) {

    }
}
