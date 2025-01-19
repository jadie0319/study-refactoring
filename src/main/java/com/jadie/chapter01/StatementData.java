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
        return new PerformanceCalculator(performance, playFor(plays, performance))
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

    private int volumeCreditsFor(Performance perf) {
        int result = 0;
        result += Math.max(perf.audience() - 30, 0);
        if ("comedy".equals(playFor(plays, perf).type())) {
            result += Math.floor(perf.audience() / 5);
        }
        return result;
    }

    public String playName(Performance perf) {
        return playFor(plays, perf).name();
    }
}
