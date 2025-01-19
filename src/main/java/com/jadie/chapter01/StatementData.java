package com.jadie.chapter01;

public record StatementData(
        String customer
) {
    public static StatementData of(String customer) {
        return new StatementData(customer);
    }
}
