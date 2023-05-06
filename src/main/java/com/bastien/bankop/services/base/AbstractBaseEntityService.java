package com.bastien.bankop.services.base;

import com.bastien.bankop.dto.base.AbstractBaseEntityDTO;
import com.bastien.bankop.entities.base.AbstractBaseEntity;
import com.bastien.bankop.mappers.base.AbstractBaseEntityMapper;
import com.bastien.bankop.services.AbstractService;
import org.springframework.data.jpa.repository.JpaRepository;

public abstract class AbstractBaseEntityService<DTO extends AbstractBaseEntityDTO, ENT extends AbstractBaseEntity>
        extends AbstractService<DTO, ENT, Long> {

    protected AbstractBaseEntityService(
            JpaRepository<ENT, Long> repository,
            AbstractBaseEntityMapper<DTO, ENT> mapper) {
        super(repository, mapper);
    }
}
