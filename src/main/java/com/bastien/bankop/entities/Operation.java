package com.bastien.bankop.entities;

import jakarta.persistence.Table;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;
import java.util.StringJoiner;

@Entity
@Table(name = "operations")
public class Operation {

    @Id
    @SequenceGenerator(name = "op_id_seq", sequenceName = "op_id_seq", initialValue = 100)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "op_id_seq")
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "id_mother", nullable = true)
    private Long idMother;

    @Column(name = "id_parent", nullable = false)
    private Long idParent;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "label", nullable = false)
    private String label;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "manually_categorized", nullable = false)
    private Boolean manuallyCategorized;

    public Operation() {}

    @Override
    public boolean equals(Object o) {
        return this == o || (o instanceof Operation op
                && Objects.equals(idMother, op.idMother)
                && idParent.equals(op.idParent)
                && date.equals(op.date)
                && label.equals(op.label)
                && price.equals(op.price));
    }

    @Override
    public int hashCode() {
        int result = (idMother != null ? idMother.hashCode() : 0);
        result = 31 * result + idParent.hashCode();
        result = 31 * result + date.hashCode();
        result = 31 * result + label.hashCode();
        result = 31 * result + price.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Operation.class.getSimpleName() + "[", "]").add("id=" + id)
                .add("idMother=" + idMother).add("idParent=" + idParent).add("date=" + date)
                .add("label='" + label + "'").add("price=" + price).add("manuallyCategorized=" + manuallyCategorized)
                .toString();
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdMother() {
        return this.idMother;
    }

    public void setIdMother(Long idMother) {
        this.idMother = idMother;
    }

    public Long getIdParent() {
        return this.idParent;
    }

    public void setIdParent(Long idParent) {
        this.idParent = idParent;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Double getPrice() {
        return this.price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Boolean getManuallyCategorized() {
        return this.manuallyCategorized;
    }

    public void setManuallyCategorized(Boolean manuallyCategorized) {
        this.manuallyCategorized = manuallyCategorized;
    }
}
