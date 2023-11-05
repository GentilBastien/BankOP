package com.bastien.bankop.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Optional;

@Setter
public class ConfigurationFilterDTO extends AbstractEntityDTO<Long> {
    @Getter
    private String name;
    private LocalDate minDate;
    private LocalDate maxDate;
    private Double minPrice;
    private Double maxPrice;
    @Getter
    private String search;
    @Getter
    private String[] selectedCategories;

    public ConfigurationFilterDTO(
            Long id, String name, LocalDate minDate, LocalDate maxDate, Double minPrice, Double maxPrice, String search,
            String[] selectedCategories) {
        super(id);
        this.name = name;
        this.minDate = minDate;
        this.maxDate = maxDate;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.search = search;
        this.selectedCategories = selectedCategories;
    }

    public Optional<LocalDate> getMinDate() {
        return Optional.ofNullable(this.minDate);
    }

    public Optional<LocalDate> getMaxDate() {
        return Optional.ofNullable(this.maxDate);
    }

    public Optional<Double> getMinPrice() {
        return Optional.ofNullable(this.minPrice);
    }

    public Optional<Double> getMaxPrice() {
        return Optional.ofNullable(this.maxPrice);
    }

}
