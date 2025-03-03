package com.jadie.chapter06;

import java.util.List;

public class Station {
    private String name;
    private List<Reading> readings;

    public Station(String name, List<Reading> readings) {
        this.name = name;
        this.readings = readings;
    }

    public String getName() {
        return name;
    }

    public List<Reading> getReadings() {
        return readings;
    }
}
