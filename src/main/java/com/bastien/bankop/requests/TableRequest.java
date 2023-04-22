package com.bastien.bankop.requests;

import java.util.Optional;

public record TableRequest(
        Optional<Long> id,
        Optional<Long> idParent,
        Optional<String> name
) {}
