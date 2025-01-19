package com.jadie.chapter01;

import java.text.DecimalFormat;

public class Statement {

    public String statement(Invoice invoice, Plays plays) throws Exception {
        int totalAmount = 0;
        int volumeCredits = 0;
        StringBuilder result = new StringBuilder("청구 내역 (고객명: %s)\n".formatted(invoice.customer()));

        for (Performance perf : invoice.performances()) {
            int thisAmount = 0;

            thisAmount = amountFor(perf, playFor(plays, perf));

            volumeCredits += Math.max(perf.audience() - 30, 0);
            if ("comedy".equals(playFor(plays, perf).type())) {
                volumeCredits += Math.floor(perf.audience() / 5);
            }
            result.append(String.format("%s: $%.2f (%d석)\n", playFor(plays, perf).name(), toDouble(thisAmount), perf.audience()));
            totalAmount += thisAmount;
        }
        result.append("총액: $%.2f\n".formatted(toDouble(totalAmount)));
        result.append("적립 포인트: %d점".formatted(volumeCredits));
        return result.toString();
    }

    private Play playFor(Plays plays, Performance perf) {
        return plays.getPlay(perf.playID());
    }

    private int amountFor(Performance aPerformance, Play play) throws Exception {
        int result = 0;
        switch (play.type()) {
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
            default -> throw new Exception("알 수 없는 장르: %s".formatted(play.type()));
        }
        return result;
    }

    private double toDouble(int amount) {
        return amount / 100.0;
    }

}
