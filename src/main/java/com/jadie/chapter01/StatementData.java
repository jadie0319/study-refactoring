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

    private int amountFor(Performance aPerformance, Plays plays) throws Exception {
        int result = 0;
        switch (playFor(plays, aPerformance).type()) {
            case "tragedy" -> {
                result = 40000;
                if (aPerformance.audience() > 30) {
                    result += 1000 * (aPerformance.audience() - 30);
                }
            }
            case "comedy" -> {
                result = 30000;
                if (aPerformance.audience() > 20) {
                    result += 10000 + 500 * (aPerformance.audience() - 20);
                }
                result += 300 * aPerformance.audience();
            }
            default -> throw new Exception("알 수 없는 장르: %s".formatted(playFor(plays, aPerformance).type()));
        }
        return result;
    }

    public int totalAmount() throws Exception {
        int result = 0;
        for (Performance perf : performances) {
            result += amountFor(perf, plays);
        }
        return result;
    }

    private double usd(int amount) {
        return amount / 100.0;
    }
}
