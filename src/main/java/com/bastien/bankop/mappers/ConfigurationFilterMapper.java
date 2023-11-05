package com.bastien.bankop.mappers;

import com.bastien.bankop.dto.ConfigurationFilterDTO;
import com.bastien.bankop.entities.ConfigurationFilter;
import com.bastien.bankop.utils.BankopUtils;
import org.springframework.stereotype.Component;

@Component
public class ConfigurationFilterMapper implements EntityMapper<ConfigurationFilterDTO, ConfigurationFilter, Long> {
    public static char SELECTED_CATEGORIES_DELIMITER = '&';

    @Override
    public ConfigurationFilter toEntity(ConfigurationFilterDTO dto) {
        final ConfigurationFilter entity = new ConfigurationFilter();
        entity.setName(dto.getName());
        if (dto.getMinDate().isPresent()) {
            entity.setMinDate(dto.getMinDate().get());
        }
        if (dto.getMaxDate().isPresent()) {
            entity.setMaxDate(dto.getMaxDate().get());
        }
        if (dto.getMinPrice().isPresent()) {
            entity.setMinPrice(dto.getMinPrice().get());
        }
        if (dto.getMaxPrice().isPresent()) {
            entity.setMaxPrice(dto.getMaxPrice().get());
        }
        entity.setSearch(dto.getSearch());
        entity.setSelectedCategories(
                BankopUtils.concatCategories(dto.getSelectedCategories(), SELECTED_CATEGORIES_DELIMITER));
        return entity;
    }

    @Override
    public ConfigurationFilterDTO toDTO(ConfigurationFilter entity) {
        return new ConfigurationFilterDTO(entity.getId(), entity.getName(), entity.getMinDate(), entity.getMaxDate(),
                                          entity.getMinPrice(), entity.getMaxPrice(), entity.getSearch(),
                                          BankopUtils.splitCategories(entity.getSelectedCategories(),
                                                                      SELECTED_CATEGORIES_DELIMITER));
    }
}
