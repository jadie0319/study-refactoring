package com.jadie.chapter01;

import java.util.Collections;
import java.util.List;

public record StatementData(
        String customer,
        List<Performance> performances,
        Plays plays
) {
    public static StatementData of(String customer, List<Performance> performances, Plays plays) {
        return new StatementData(
                customer,
                Collections.unmodifiableList(performances),
                plays
        );
    }

    private Play playFor(Plays plays, Performance perf) {
        return plays.getPlay(perf.playID());
    }

    public int amountFor(Performance performance) {
        return createPerformanceCalculator(performance, playFor(plays, performance)).amount();
    }

    public int totalAmount() {
        return performances.stream()
                .mapToInt(this::amountFor)
                .sum();
    }

    public int totalVolumeCredits() {
        return performances.stream()
                .mapToInt(this::volumeCreditsFor)
                .sum();
    }

    private int volumeCreditsFor(Performance perf) {
        return createPerformanceCalculator(perf, playFor(plays, perf)).volumeCredits();
    }

    public String playName(Performance perf) {
        return playFor(plays, perf).name();
    }

    private PerformanceCalculator createPerformanceCalculator(Performance performance, Play play) {
        return switch (play.type()) {
            case "tragedy" -> new TragedyCalculator(performance, play);
            case "comedy" -> new ComedyCalculator(performance, play);
            default -> throw new RuntimeException("알 수 없는 장르: %s".formatted(play.type()));
        };
    }

}
