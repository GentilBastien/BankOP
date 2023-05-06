package com.bastien.bankop.dto.base;

import lombok.Setter;

import java.time.LocalDate;
import java.util.Optional;

@Setter
public class OperationDTO extends AbstractBaseEntityDTO {

    private Long idMother;
    private LocalDate date;
    private Double price;
    private Boolean manuallyCategorized;

    public OperationDTO(Long id, Long idCategory, String name) {
        super(id, idCategory, name);
    }

    public Optional<Long> getIdMother() {
        return Optional.ofNullable(this.idMother);
    }

    public Optional<LocalDate> getDate() {
        return Optional.ofNullable(this.date);
    }

    public Optional<Double> getPrice() {
        return Optional.ofNullable(this.price);
    }

    public Optional<Boolean> getManuallyCategorized() {
        return Optional.ofNullable(this.manuallyCategorized);
    }
}
