package com.bastien.bankop.entities;

import jakarta.persistence.*;

import java.util.Objects;
import java.util.StringJoiner;

@Entity
@jakarta.persistence.Table(name = "tables")
public class Table {

    @Id
    @SequenceGenerator(name = "table_id_seq", sequenceName = "table_id_seq", initialValue = 15)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "table_id_seq")
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "id_parent", nullable = true)
    private Long idParent;

    @Column(name = "name", nullable = false)
    private String name;

    public Table() {
    }

    @Override
    public boolean equals(Object o) {
        return this == o || (o instanceof Table table
                        && Objects.equals(idParent, table.idParent)
                        && name.equals(table.name));
    }

    @Override
    public int hashCode() {
        int result = (idParent != null ? idParent.hashCode() : 0);
        result = 31 * result + name.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Table.class.getSimpleName() + "[", "]").add("id=" + id)
                .add("idParent=" + idParent).add("name=\"" + name + "\"").toString();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdParent() {
        return idParent;
    }

    public void setIdParent(Long parentId) {
        this.idParent = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
