package com.jadie.chapter06;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class StationTest {

    @DisplayName("온도가 min 보다 작거나 max 보다 크면 true 를 반환한다.")
    @Test
    void outsideRange() {
        Station station = new Station("ZB1", defaultReadings());
        OperatingPlan operatingPlan = new OperatingPlan(45, 60);

        boolean result = readingsOutsideRange(
                station,
                operatingPlan.temperatureFloor(),
                operatingPlan.temperatureCeiling()
        );

        assertThat(result).isFalse();
    }

    public boolean readingsOutsideRange(Station station, Integer min, Integer max) {
        List<Reading> readings = station.getReadings();
        return readings.stream()
                .anyMatch(r -> r.temp() < min || r.temp() > max);
    }

    private static List<Reading> defaultReadings() {
        return List.of(
                new Reading(47, LocalDateTime.of(2016, 11, 10, 9, 10)),
                new Reading(53, LocalDateTime.of(2016, 11, 10, 9, 20)),
                new Reading(58, LocalDateTime.of(2016, 11, 10, 9, 30)),
                new Reading(53, LocalDateTime.of(2016, 11, 10, 9, 40)),
                new Reading(51, LocalDateTime.of(2016, 11, 10, 9, 50))
        );
    }

}
