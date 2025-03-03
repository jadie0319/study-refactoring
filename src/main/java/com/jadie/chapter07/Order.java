package com.jadie.chapter07;

import java.util.List;

public class Order {
    private String priority;

    public boolean higherThanNormal(List<Order> orders) {
        return orders.stream()
                .anyMatch(o -> "high".equals(o.priority) || "rush".equals(o.priority));
    }
}
