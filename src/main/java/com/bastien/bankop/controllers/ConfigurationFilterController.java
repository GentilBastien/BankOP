package com.bastien.bankop.controllers;

import com.bastien.bankop.dto.ConfigurationFilterDTO;
import com.bastien.bankop.entities.ConfigurationFilter;
import com.bastien.bankop.services.ConfigurationFilterService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/config-filter")
public class ConfigurationFilterController extends AbstractEntityController<ConfigurationFilterDTO, ConfigurationFilter, Long> {
    public ConfigurationFilterController(ConfigurationFilterService service) {
        super(service);
    }
}
