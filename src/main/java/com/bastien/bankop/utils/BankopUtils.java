package com.bastien.bankop.utils;

import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

public class BankopUtils {

    public static <E> List<E> emptyListToNull(List<E> list) {
        Objects.requireNonNull(list, "emptyListToNull needs non-null argument.");
        return list.isEmpty() ? null : list;
    }

    public static String[] splitCategories(String categoryString, char delimiter) {
        return categoryString.isEmpty() ? new String[0] : StringUtils.split(categoryString, String.valueOf(delimiter));
    }

    public static String concatCategories(String[] categories, char delimiter) {
        return categories.length == 0 ? "" : String.join(String.valueOf(delimiter), categories);
    }
}
