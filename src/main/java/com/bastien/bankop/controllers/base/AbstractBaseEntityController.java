package com.bastien.bankop.controllers.base;

import com.bastien.bankop.controllers.AbstractEntityController;
import com.bastien.bankop.dto.base.AbstractBaseEntityDTO;
import com.bastien.bankop.entities.base.AbstractBaseEntity;
import com.bastien.bankop.services.base.AbstractBaseEntityService;

public abstract class AbstractBaseEntityController<DTO extends AbstractBaseEntityDTO, ENT extends AbstractBaseEntity>
        extends AbstractEntityController<DTO, ENT, Long> {

    public AbstractBaseEntityController(AbstractBaseEntityService<DTO, ENT> service) {
        super(service);
    }
}
