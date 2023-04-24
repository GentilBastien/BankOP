package com.bastien.bankop.dto;

import java.util.Objects;
import java.util.Optional;
import java.util.StringJoiner;

public class TableDTO {

    private final Long id;

    private final Long idParent;

    private final String name;

    public TableDTO() {
        this.id = null;
        this.idParent = null;
        this.name = null;
    }

    public TableDTO(Long idParent, String name) {
        this.id = null;
        this.idParent = idParent;
        this.name = Objects.requireNonNull(name);
    }

    public TableDTO(Long id, Long idParent, String name) {
        this.id = Objects.requireNonNull(id);
        this.idParent = idParent;
        this.name = Objects.requireNonNull(name);
    }

    @Override
    public boolean equals(Object o) {
        return this == o ||
                (o instanceof TableDTO dto
                        && Objects.equals(this.id, dto.id)
                        && Objects.equals(this.idParent, dto.idParent)
                        && Objects.equals(this.name, dto.name));
    }

    @Override
    public int hashCode() {
        int result = this.id != null ? this.id.hashCode() : 0;
        result = 31 * result + (this.idParent != null ? this.idParent.hashCode() : 0);
        return  31 * result + (this.name != null ? this.name.hashCode() : 0);
    }

    @Override
    public String toString() {
        return new StringJoiner(",\n", "{\n", "}")
                .add("\tid=" + id)
                .add("\tidParent=" + idParent)
                .add("\tname='" + name + "'\n")
                .toString();
    }

    public Optional<Long> getId() {
        return Optional.ofNullable(this.id);
    }

    public Optional<Long> getIdParent() {
        return Optional.ofNullable(this.idParent);
    }

    public Optional<String> getName() {
        return Optional.ofNullable(this.name);
    }
}