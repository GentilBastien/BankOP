package com.bastien.bankop.dto;

import java.time.format.DateTimeFormatter;

public interface GenericDTO {
    DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
}
