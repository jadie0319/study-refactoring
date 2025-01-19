package com.jadie.chapter01;

public class Statement {

    public String statement(Invoice invoice, Plays plays) throws Exception {
        StatementData data = StatementData.of(invoice.customer(), invoice.performances(), plays);

        return renderPlainText(data);
    }

    private String renderPlainText(StatementData data) throws Exception {
        StringBuilder result = new StringBuilder("청구 내역 (고객명: %s)\n".formatted(data.customer()));

        for (Performance perf : data.performances()) {
            result.append(String.format("%s: $%.2f (%d석)\n", data.playName(perf), usd(data.amountFor(perf)), perf.audience()));
        }

        result.append("총액: $%.2f\n".formatted(usd(data.totalAmount())));
        result.append("적립 포인트: %d점".formatted(data.totalVolumeCredits()));
        return result.toString();
    }

    private double usd(int amount) {
        return amount / 100.0;
    }

}
