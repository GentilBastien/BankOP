package com.bastien.bankop.utils;

import java.util.List;
import java.util.Objects;

public class BankopUtils {

    public static <E> List<E> emptyListToNull(List<E> list) {
        Objects.requireNonNull(list, "emptyListToNull needs non-null argument.");
        return list.isEmpty() ? null : list;
    }
}
