package com.jadie.chapter01;

import java.util.List;

public record Invoice(
        String customer,
        List<Performance> performances
) {
}
