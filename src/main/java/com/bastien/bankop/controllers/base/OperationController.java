package com.bastien.bankop.controllers.base;

import com.bastien.bankop.dto.base.OperationDTO;
import com.bastien.bankop.entities.base.Operation;
import com.bastien.bankop.services.base.OperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/operations")
public class OperationController extends AbstractBaseEntityController<OperationDTO, Operation> {

    private final OperationService service;

    @Autowired
    public OperationController(OperationService service) {
        super(service);
        this.service = service;
    }

    @PostMapping(path = "auto-classify")
    public List<OperationDTO> autoClassifyOperations(@RequestBody List<OperationDTO> operationDTOList) {
        return this.service.classifyOperationsByKeywords(operationDTOList);
    }
}
