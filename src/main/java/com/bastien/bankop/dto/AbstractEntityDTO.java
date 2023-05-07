package com.bastien.bankop.dto;

import lombok.Setter;

import java.util.Optional;

@Setter
public abstract class AbstractEntityDTO<ID>  implements GenericDTO {
    private final ID id;

    public AbstractEntityDTO(ID id) {
        this.id = id;
    }

    public Optional<ID> getId() {
        return Optional.ofNullable(this.id);
    }
}
