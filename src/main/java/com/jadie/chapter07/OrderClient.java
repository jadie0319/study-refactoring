package com.jadie.chapter07;

import java.util.List;

public class OrderClient {

    public long highPriorityCount(List<OldOrder> oldOrders) {
        long highPriorityCount = oldOrders.stream()
                .filter(OldOrder::isLegalPriority)
                .count();
        return highPriorityCount;
    }


}
