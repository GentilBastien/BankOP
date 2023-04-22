package com.bastien.bankop.requests;

import java.util.Optional;

public record KeywordRequest(
        Optional<String> keyword,
        Optional<Long> idParent
) {}
