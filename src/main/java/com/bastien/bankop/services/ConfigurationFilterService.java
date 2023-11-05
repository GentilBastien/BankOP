package com.bastien.bankop.services;

import com.bastien.bankop.dto.ConfigurationFilterDTO;
import com.bastien.bankop.entities.ConfigurationFilter;
import com.bastien.bankop.exceptions.MalFormedEntityException;
import com.bastien.bankop.mappers.ConfigurationFilterMapper;
import com.bastien.bankop.repositories.ConfigurationFilterRepository;
import org.springframework.stereotype.Service;

@Service
public class ConfigurationFilterService extends AbstractService<ConfigurationFilterDTO, ConfigurationFilter, Long> {


    protected ConfigurationFilterService(ConfigurationFilterRepository repository, ConfigurationFilterMapper mapper) {
        super(repository, mapper);
    }

    @Override
    protected void validateEntity(ConfigurationFilter entity) throws MalFormedEntityException {
        if (this.getEntities().stream().anyMatch(filter -> entity.getName().equals(filter.getName()))) {
            throw new IllegalStateException(
                    "The name " + entity.getId() + " is already taken by an other Configuration Filter.");
        }
    }

    @Override
    protected void validateEntityBeforeDelete(ConfigurationFilter entity) throws IllegalStateException {

    }
}
