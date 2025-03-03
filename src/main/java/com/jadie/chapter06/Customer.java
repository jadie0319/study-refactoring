package com.jadie.chapter06;

import java.util.List;
import java.util.stream.Stream;

public class Customer {
    private String state;

    public boolean isNewEngland(Customer customer) {
        return isNewEngland(customer.state);
    }

    public boolean isNewEngland(String state) {
        return Stream.of("MA","CT","ME","VT","NH","RI")
                .anyMatch(k -> k.equals(state));
    }

    public void kkk(List<Customer> customer) {
        List<Customer> englandCustomers = customer.stream()
                .filter((k) -> isNewEngland(k.state))
                .toList();
    }
}
