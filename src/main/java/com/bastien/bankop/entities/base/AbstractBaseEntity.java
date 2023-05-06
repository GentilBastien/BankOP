package com.bastien.bankop.entities.base;

import com.bastien.bankop.entities.AbstractEntity;

public abstract class AbstractBaseEntity extends AbstractEntity<Long> {

    public abstract Table getCategory();

    public abstract void setCategory(Table category);

    public abstract String getName();

    public abstract void setName(String name);
}
