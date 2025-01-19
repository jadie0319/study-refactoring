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
        return PerformanceCalculatorFactory.createCalculator(performance, playFor(plays, performance))
                .amount();
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

    private int volumeCreditsFor(Performance performance) {
        return PerformanceCalculatorFactory.createCalculator(performance, playFor(plays, performance))
                .volumeCredits();
    }

    public String playName(Performance perf) {
        return playFor(plays, perf).name();
    }
}
