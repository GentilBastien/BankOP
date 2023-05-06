package com.bastien.bankop.entities.base;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@FieldNameConstants
@jakarta.persistence.Table(name = "tables")
public class Table extends AbstractBaseEntity {

    @Id
    @SequenceGenerator(name = "table_id_seq", sequenceName = "table_id_seq", initialValue = 15)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "table_id_seq")
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_category")
    private Table category;

    @OneToMany(mappedBy = Fields.category)
    private Set<Table> tables;

    @OneToMany(mappedBy = Operation.Fields.category)
    private Set<Operation> operations;

    @OneToMany(mappedBy = Keyword.Fields.category)
    private Set<Keyword> keywords;

    @Column(name = "name", nullable = false)
    private String name;
}
