package com.bastien.bankop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class ResponseImportOperationDTO implements GenericDTO {
    public static String NONE = "none";
    public static String FILE = "file";
    public static String DATABASE = "releve";

    private String doublon;
    private LocalDate date;
    private String name;
    private Double price;
    private Long categoryId;
    private String categoryName;

    public boolean equals(Object o) {
        return this == o ||
                (o instanceof ResponseImportOperationDTO dto
                        && date.equals(dto.date)
                        && name.equals(dto.name)
                        && price.equals(dto.price)
                        && categoryId.equals(dto.categoryId)
                );
    }
}
