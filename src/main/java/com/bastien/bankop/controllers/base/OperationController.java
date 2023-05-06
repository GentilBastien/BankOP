package com.bastien.bankop.controllers.base;

import com.bastien.bankop.controllers.AbstractEntityController;
import com.bastien.bankop.dto.base.OperationDTO;
import com.bastien.bankop.entities.base.Operation;
import com.bastien.bankop.services.base.AbstractBaseEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/operations")
public class OperationController extends AbstractEntityController<OperationDTO, Operation, Long> {

    @Autowired
    public OperationController(AbstractBaseEntityService<OperationDTO, Operation> service) {
        super(service);
    }
}
