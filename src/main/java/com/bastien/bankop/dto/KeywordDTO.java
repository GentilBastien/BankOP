package com.bastien.bankop.dto;

import java.util.Objects;
import java.util.Optional;
import java.util.StringJoiner;

public class KeywordDTO {

    private final String name;

    private final Long idParent;

    public KeywordDTO() {
        this.name = null;
        this.idParent = null;
    }

    public KeywordDTO(String name) {
        this.name = Objects.requireNonNull(name);
        this.idParent = null;
    }

    public KeywordDTO(String name, Long idParent) {
        this.name = Objects.requireNonNull(name);
        this.idParent = Objects.requireNonNull(idParent);
    }

    @Override
    public boolean equals(Object o) {
        return this == o ||
                (o instanceof KeywordDTO dto
                        && Objects.equals(this.name, dto.name)
                        && Objects.equals(this.idParent, dto.idParent));
    }

    @Override
    public int hashCode() {
        int result = this.name != null ? this.name.hashCode() : 0;
        return 31 * result + (this.idParent != null ? this.idParent.hashCode() : 0);
    }

    @Override
    public String toString() {
        return new StringJoiner(",\n", "{\n", "}")
                .add("\tname=" + name)
                .add("\tidParent='" + idParent + "'\n")
                .toString();
    }

    public Optional<String> getName() {
        return Optional.ofNullable(this.name);
    }

    public Optional<Long> getIdParent() {
        return Optional.ofNullable(this.idParent);
    }
}
