package com.jadie.chapter06;

import java.time.LocalDateTime;

public class Reading {
    private Integer temp;
    private LocalDateTime time;

    public Reading(Integer temp, LocalDateTime time) {
        this.temp = temp;
        this.time = time;
    }

    public Integer temp() {
        return temp;
    }

    public LocalDateTime time() {
        return time;
    }
}
