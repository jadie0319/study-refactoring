package com.jadie.chapter07;

import java.util.Arrays;
import java.util.stream.Stream;

public class Priority {
    private String priority;
    public Priority(String value) {
        if (isLegalValue(value)) {
            this.priority = value;
        } else {
            throw new RuntimeException("유효하지 않은 우선순위 입니다.");
        }
    }

    @Override
    public String toString() {
        return priority;
    }

    private boolean isLegalValue(String value) {
        return Stream.of("low", "normal", "high", "rush")
                .anyMatch( v -> v.equals(value));
    }

    public boolean isLegalPriority() {
        return isLegalValue(priority);
    }


    //    public boolean higherThan(Priority normal) {
//        return "high".equals(priority) || "rush".equals(o.priority);
//    }
}
