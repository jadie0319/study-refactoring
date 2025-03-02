package com.jadie.chapter06;

import java.util.List;
import java.util.stream.Stream;

public class Customer {
    private String state;

    public boolean isNewEngland(Customer customer) {
        String state = customer.state;
        return Stream.of("MA","CT","ME","VT","NH","RI")
                .anyMatch(k -> k.equals(state));
    }

    public boolean xxIsNewEngland(String state) {
        return Stream.of("MA","CT","ME","VT","NH","RI")
                .anyMatch(k -> k.equals(state));
    }

    public void kkk(List<Customer> customer) {
        List<Customer> englandCustomers = customer.stream()
                .filter(this::isNewEngland)
                .toList();
    }
}
