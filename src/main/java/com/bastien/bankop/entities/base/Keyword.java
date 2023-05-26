package com.bastien.bankop.entities.base;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

@Getter
@Setter
@Entity
@NoArgsConstructor
@FieldNameConstants
@jakarta.persistence.Table(name = "keywords")
public class Keyword extends AbstractBaseEntity {
    
    @Id
    @SequenceGenerator(name = "kw_id_seq", sequenceName = "kw_id_seq", initialValue = 100)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "kw_id_seq")
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_category", nullable = false)
    private Table category;

    @Column(name = "keyword", nullable = false)
    private String name;
}
