package com.bastien.bankop.dto;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

public class OperationDTO {

    private final Long id;

    private final Long idParent;

    private final Long idMother;

    private final LocalDate date;

    private final String label;

    private final Double price;

    private final Boolean manuallyCategorized;

    public OperationDTO() {
        this.id = null;
        this.idParent = null;
        this.idMother = null;
        this.date = null;
        this.label = null;
        this.price = null;
        this.manuallyCategorized = null;
    }

    public OperationDTO(LocalDate date, String label, Double price) {
        this.id = null;
        this.idParent = null;
        this.idMother = null;
        this.date = Objects.requireNonNull(date);
        this.label = Objects.requireNonNull(label);
        this.price = Objects.requireNonNull(price);
        this.manuallyCategorized = null;
    }

    public OperationDTO(Long idParent, LocalDate date, String label, Double price) {
        this.id = null;
        this.idParent = idParent;
        this.idMother = null;
        this.date = Objects.requireNonNull(date);
        this.label = Objects.requireNonNull(label);
        this.price = Objects.requireNonNull(price);
        this.manuallyCategorized = null;
    }

    public OperationDTO(Long idParent, Long idMother, LocalDate date, String label, Double price,
                        Boolean manuallyCategorized) {
        this.id = null;
        this.idParent = idParent;
        this.idMother = idMother;
        this.date = Objects.requireNonNull(date);
        this.label = Objects.requireNonNull(label);
        this.price = Objects.requireNonNull(price);
        this.manuallyCategorized = manuallyCategorized;
    }

    public OperationDTO(
            Long id, Long idParent, Long idMother, LocalDate date, String label, Double price,
            Boolean manuallyCategorized) {
        this.id = id;
        this.idParent = idParent;
        this.idMother = idMother;
        this.date = Objects.requireNonNull(date);
        this.label = Objects.requireNonNull(label);
        this.price = Objects.requireNonNull(price);
        this.manuallyCategorized = manuallyCategorized;
    }


    public Optional<Long> getId() {
        return Optional.ofNullable(this.id);
    }

    public Optional<Long> getIdParent() {
        return Optional.ofNullable(this.idParent);
    }

    public Optional<Long> getIdMother() {
        return Optional.ofNullable(this.idMother);
    }

    public Optional<LocalDate> getDate() {
        return Optional.ofNullable(this.date);
    }

    public Optional<String> getLabel() {
        return Optional.ofNullable(this.label);
    }

    public Optional<Double> getPrice() {
        return Optional.ofNullable(this.price);
    }

    public Optional<Boolean> getManuallyCategorized() {
        return Optional.ofNullable(this.manuallyCategorized);
    }

}
