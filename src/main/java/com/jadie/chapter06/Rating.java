package com.jadie.chapter06;

public class Rating {

    public int getRating(Driver driver) {
        return driver.getNumberOfLateDeliveries() > 5 ? 2 : 1;
    }

    public boolean moreThanFiveLateDeliveries(Driver driver) {
        return driver.getNumberOfLateDeliveries() > 5;
    }
}
