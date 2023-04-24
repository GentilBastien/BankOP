package com.bastien.bankop.endpoints.importoperation;

import com.bastien.bankop.controllers.OperationClassifierController;
import com.bastien.bankop.controllers.OperationController;
import com.bastien.bankop.controllers.TableController;
import com.bastien.bankop.dto.OperationDTO;
import com.bastien.bankop.dto.TreeTableDTO;
import com.bastien.bankop.exceptions.MalFormedDTOException;
import com.bastien.bankop.mappers.TreeTableMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/import")
public class ImportOperationController {

    private final OperationClassifierController operationClassifierController;

    private final OperationController operationController;

    private final TreeTableMapper treeTableMapper;


    public ImportOperationController(
            @Autowired OperationClassifierController operationClassifierController,
            @Autowired TableController tableController,
            @Autowired OperationController operationController
    ) {
        this.operationClassifierController = operationClassifierController;
        this.operationController = operationController;
        this.treeTableMapper = new TreeTableMapper(tableController);
    }

    public List<OperationDTO> reclassifyOperationDTO(List<OperationDTO> operationDTOList) {
        return operationDTOList.stream()
                .map(o -> {
                    String label = o.getLabel().orElseThrow(() -> new MalFormedDTOException("label."));
                    return new OperationDTO(
                            this.operationClassifierController.findIdParentByKeywords(label),
                            o.getDate().orElseThrow(() -> new MalFormedDTOException("id.")),
                            label,
                            o.getPrice().orElseThrow(() -> new MalFormedDTOException("id."))
                    );
                })
                .toList();
    }

    @GetMapping
    public TreeTableDTO mapTreeTable() {
        return this.treeTableMapper.buildTreeTableDTO();
    }

    @PostMapping
    public void pushOperations(@RequestBody List<OperationDTO> operationDTOList) {
        operationDTOList.forEach(this.operationController::registerOperation);
    }
}
