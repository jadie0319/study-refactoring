package com.jadie.chapter01;

import java.text.DecimalFormat;

public class Statement {

    public String statement(Invoice invoice, Plays plays) throws Exception {
        int totalAmount = 0;
        int volumeCredits = 0;
        StringBuilder result = new StringBuilder("청구 내역 (고객명: %s)\n".formatted(invoice.customer()));

        for (Performance perf : invoice.performances()) {
            Play play = plays.getPlay(perf.playID());
            int thisAmount = 0;

            thisAmount = amountFor(perf, play);

            volumeCredits += Math.max(perf.audience() - 30, 0);
            if ("comedy".equals(play.type())) {
                volumeCredits += Math.floor(perf.audience() / 5);
            }
            result.append(String.format("%s: $%.2f (%d석)\n", play.name(), toDouble(thisAmount), perf.audience()));
            totalAmount += thisAmount;
        }
        result.append("총액: $%.2f\n".formatted(toDouble(totalAmount)));
        result.append("적립 포인트: %d점".formatted(volumeCredits));
        return result.toString();
    }

    private static int amountFor(Performance perf, Play play) throws Exception {
        int result = 0;
        switch (play.type()) {
            case "tragedy" -> {
                result = 40000;
                if (perf.audience() > 30) {
                    result += 1000 * (perf.audience() - 30);
                }
            }
            case "comedy" -> {
                result = 30000;
                if (perf.audience() > 20) {
                    result += 10000 + 500 * (perf.audience() - 20);
                }
                result += 300 * perf.audience();
            }
            default -> throw new Exception("알 수 없는 장르: %s".formatted(play.type()));
        }
        return result;
    }

    private double toDouble(int amount) {
        return amount / 100.0;
    }

}
