package com.bastien.bankop.endpoints.importoperation;

import com.bastien.bankop.controllers.OperationClassifierController;
import com.bastien.bankop.controllers.OperationController;
import com.bastien.bankop.controllers.TableController;
import com.bastien.bankop.dto.OperationDTO;
import com.bastien.bankop.dto.TableDTO;
import com.bastien.bankop.entities.Table;
import com.bastien.bankop.exceptions.MalFormedDTOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/import")
public class ImportOperationController {

    private final OperationClassifierController operationClassifierController;
    private final TableController tableController;
    private final OperationController operationController;

    public ImportOperationController(
            @Autowired OperationClassifierController operationClassifierController,
            @Autowired TableController tableController,
            @Autowired OperationController operationController
    ) {
        this.operationClassifierController = operationClassifierController;
        this.tableController = tableController;
        this.operationController = operationController;
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

    public List<TableDTO> listTableDTO() {
        return this.tableController.listTables()
                .stream()
                .map(Table::getId)
                .map(this.tableController::mapToDTO)
                .toList();
    }

    public void push(List<OperationDTO> operationDTOList) {
        operationDTOList.forEach(this.operationController::registerOperation);
    }
}
