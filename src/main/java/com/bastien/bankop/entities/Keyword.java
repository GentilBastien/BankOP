package com.bastien.bankop.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.StringJoiner;

@Entity
@Table(name = "keywords")
public class Keyword {

    @Id
    @Column(name = "keyword", nullable = false)
    private String keyword;

    @Column(name = "id_parent", nullable = false)
    private Long idParent;

    public Keyword() {
    }

    @Override
    public boolean equals(Object o) {
        return this == o
                || (o instanceof Keyword kw
                && this.keyword.equals(kw.keyword))
                && this.idParent.equals(kw.idParent);
    }

    @Override
    public int hashCode() {
        int result = this.idParent.hashCode();
        result = 31 * result + this.keyword.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Keyword.class.getSimpleName() + "[", "]")
                .add("keyword='" + keyword + "'")
                .add("idParent=" + idParent)
                .toString();
    }

    public String getKeyword() {
        return this.keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Long getIdParent() {
        return this.idParent;
    }

    public void setIdParent(Long idParent) {
        this.idParent = idParent;
    }
}
