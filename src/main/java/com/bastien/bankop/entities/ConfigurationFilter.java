package com.bastien.bankop.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@NoArgsConstructor
@FieldNameConstants
@jakarta.persistence.Table(name = "filters")
public class ConfigurationFilter extends AbstractEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "min_date")
    private LocalDate minDate;

    @Column(name = "max_date")
    private LocalDate maxDate;

    @Column(name = "min_price")
    private Double minPrice;

    @Column(name = "max_price")
    private Double maxPrice;

    @Column(name = "search", nullable = false)
    private String search;

    @Column(name = "selected_categories", nullable = false)
    private String selectedCategories;
}
