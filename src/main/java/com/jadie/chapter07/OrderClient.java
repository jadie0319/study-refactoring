package com.jadie.chapter07;

import java.util.List;

public class OrderClient {

    public long highPriorityCount(List<Order> orders) {
        long highPriorityCount = orders.stream()
                .filter(Order::isLegalPriority)
                .count();
        return highPriorityCount;
    }

}
