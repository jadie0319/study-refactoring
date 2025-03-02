package com.jadie.chapter06;

import java.util.List;

public class Invoice {
    private String customer;
    private List<Order> orders;

    public Invoice(String customer, List<Order> orders) {
        this.customer = customer;
        this.orders = orders;
    }


    public String getCustomer() {
        return customer;
    }

    public List<Order> getOrders() {
        return orders;
    }
}
