package com.bastien.bankop.entities;

import com.bastien.bankop.entities.base.AbstractBaseEntity;

import java.util.Objects;

public abstract class AbstractEntity<ID> {

    public abstract ID getId();

    public abstract void setId(ID id);

    @Override
    public boolean equals(Object obj) {
        return obj instanceof AbstractBaseEntity ba && Objects.equals(this.getId(), ba.getId());
    }
}
