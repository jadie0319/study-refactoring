package com.jadie.chapter06;

public class OperatingPlan {
    private Integer temperatureFloor;
    private Integer temperatureCeiling;

    public OperatingPlan(Integer temperatureFloor, Integer temperatureCeiling) {
        this.temperatureFloor = temperatureFloor;
        this.temperatureCeiling = temperatureCeiling;
    }

    public Integer temperatureFloor() {
        return temperatureFloor;
    }

    public Integer temperatureCeiling() {
        return temperatureCeiling;
    }
}
