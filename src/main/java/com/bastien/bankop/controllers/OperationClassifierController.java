package com.bastien.bankop.controllers;

import com.bastien.bankop.dto.OperationDTO;
import com.bastien.bankop.entities.Keyword;
import com.bastien.bankop.entities.Operation;
import com.bastien.bankop.services.OperationClassifierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/classifier")
public class OperationClassifierController {

    private final OperationClassifierService operationClassifierService;

    private final KeywordController keywordController;

    private final OperationController operationController;

    public OperationClassifierController(
            @Qualifier("operationClassifier") OperationClassifierService operationClassifierService,
            @Autowired TableController tableController,
            @Autowired KeywordController keywordController,
            @Autowired OperationController operationController) {
        this.operationClassifierService = operationClassifierService;
        this.keywordController = keywordController;
        this.operationController = operationController;
    }

    @GetMapping(path = "/{label}")
    public Long findIdParentByKeywords(@PathVariable String label) {
        List<Keyword> keywords = this.keywordController.listKeywords();
        return this.operationClassifierService.findIdParentByKeywords(label, keywords);
    }

    @Deprecated
    public Long findIdParentByScalar(OperationDTO opDTO) {
        List<OperationDTO> operations = this.operationController.listOperations()
                .stream()
                .map(Operation::getId)
                .map(this.operationController::mapToDTO)
                .toList();
        return this.operationClassifierService.findIdParentByScalar(opDTO, operations);
    }
}
