package com.jadie.chapter07;

import java.util.List;

public class OrderClient {

    public long highPriorityCount(List<Order> orders) {
        long highPriorityCount = orders.stream()
                .filter(o -> "high".equals(o.getPriority()) || "rush".equals(o.getPriority()))
                .count();
        return highPriorityCount;
    }

}
