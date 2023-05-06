package com.bastien.bankop.POUBELLE;

import com.bastien.bankop.controllers.base.KeywordController;
import com.bastien.bankop.controllers.base.OperationController;
import com.bastien.bankop.controllers.base.TableController;
import com.bastien.bankop.dto.base.OperationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

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
//        List<Keyword> keywords = this.keywordController.listKeywords();
//        return this.operationClassifierService.findIdParentByKeywords(label, keywords);
        return null;
    }

    @Deprecated
    public Long findIdParentByScalar(OperationDTO opDTO) {
        return null;
    }
}
