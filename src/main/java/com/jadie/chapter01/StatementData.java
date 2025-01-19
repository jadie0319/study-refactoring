package com.jadie.chapter01;

import java.util.Collections;
import java.util.List;

public record StatementData(
        String customer,
        List<Performance> performances
) {
    public static StatementData of(String customer, List<Performance> performances) {
        return new StatementData(
                customer,
                Collections.unmodifiableList(performances)
        );
    }
}
