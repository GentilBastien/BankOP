package com.bastien.bankop.entities.base;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

import java.time.LocalDate;
import java.util.Set;
import java.util.StringJoiner;

@Getter
@Setter
@Entity
@NoArgsConstructor
@FieldNameConstants
@jakarta.persistence.Table(name = "operations")
public class Operation extends AbstractBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "id_mother")
    private Operation mother;
    @OneToMany(mappedBy = Fields.mother)
    private Set<Operation> subOperations;
    @ManyToOne
    @JoinColumn(name = "id_category", nullable = false)
    private Table category;
    @Column(name = "date", nullable = false)
    private LocalDate date;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "price", nullable = false)
    private Double price;
    @Column(name = "manually_categorized", nullable = false)
    private Boolean manuallyCategorized;

    @Override
    public String toString() {
        return new StringJoiner(", ", Operation.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("mother=" + mother)
                .add("subOperations=" + subOperations)
                .add("category=" + category)
                .add("date=" + date)
                .add("name='" + name + "'")
                .add("price=" + price)
                .add("manuallyCategorized=" + manuallyCategorized)
                .toString();
    }
}
