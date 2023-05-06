package com.bastien.bankop.dto.base;

public class TableDTO extends AbstractBaseEntityDTO {
    public TableDTO(Long id, Long idCategory, String name) {
        super(id, idCategory, name);
    }
}
