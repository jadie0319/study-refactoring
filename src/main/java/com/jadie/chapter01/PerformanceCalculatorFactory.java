package com.jadie.chapter01;

public class PerformanceCalculatorFactory {
    public static PerformanceCalculator createCalculator(Performance performance, Play play) {
        return switch (play.type()) {
            case "tragedy" -> new TragedyCalculator(performance, play);
            case "comedy" -> new ComedyCalculator(performance, play);
            default -> throw new RuntimeException("알 수 없는 장르: %s".formatted(play.type()));
        };
    }
}
