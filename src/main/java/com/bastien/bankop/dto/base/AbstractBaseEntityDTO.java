package com.bastien.bankop.dto.base;

import com.bastien.bankop.dto.AbstractEntityDTO;
import lombok.Setter;

import java.util.Optional;

@Setter
public abstract class AbstractBaseEntityDTO extends AbstractEntityDTO<Long> {
    private Long idCategory;
    private String name;

    public AbstractBaseEntityDTO(Long id, Long idCategory, String name) {
        super(id);
        this.idCategory = idCategory;
        this.name = name;
    }

    public Optional<Long> getIdCategory() {
        return Optional.ofNullable(this.idCategory);
    }

    public Optional<String> getName() {
        return Optional.ofNullable(this.name);
    }
}
