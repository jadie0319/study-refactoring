package com.jadie.chapter01;

import java.util.List;

public class Statement {

    public String statement(Invoice invoice, Plays plays) throws Exception {
        StatementData data = StatementData.of(invoice.customer(), invoice.performances());

        return renderPlainText(data, plays);
    }

    private String renderPlainText(StatementData data, Plays plays) throws Exception {
        StringBuilder result = new StringBuilder("청구 내역 (고객명: %s)\n".formatted(data.customer()));

        for (Performance perf : data.performances()) {
            result.append(String.format("%s: $%.2f (%d석)\n", playFor(plays, perf).name(), usd(amountFor(perf, plays)), perf.audience()));
        }

        result.append("총액: $%.2f\n".formatted(usd(totalAmount(data.performances(), plays))));
        result.append("적립 포인트: %d점".formatted(totalVolumeCredits(data.performances(), plays)));
        return result.toString();
    }

    private int totalAmount(List<Performance> performances, Plays plays) throws Exception {
        int result = 0;
        for (Performance perf : performances) {
            result += amountFor(perf, plays);
        }
        return result;
    }

    private int totalVolumeCredits(List<Performance> performances, Plays plays) {
        int result = 0;
        for (Performance perf : performances) {
            result += volumeCreditsFor(plays, perf);
        }
        return result;
    }

    private int volumeCreditsFor(Plays plays, Performance perf) {
        int result = 0;
        result += Math.max(perf.audience() - 30, 0);
        if ("comedy".equals(playFor(plays, perf).type())) {
            result += Math.floor(perf.audience() / 5);
        }
        return result;
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

    private double usd(int amount) {
        return amount / 100.0;
    }

}
