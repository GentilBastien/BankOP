package com.bastien.bankop.requests;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDate;
import java.util.Optional;

public record OperationRequest(
        Optional<Long> id,
        Optional<Long> idMother,
        Optional<Long> idParent,
        Optional<LocalDate> date,
        Optional<String> label,
        Optional<Double> price,
        Optional<Boolean> manuallyCategorized
) {}
