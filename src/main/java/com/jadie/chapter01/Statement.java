package com.jadie.chapter01;

public class Statement {

    public String statement(Invoice invoice, Plays plays) throws Exception {
        StatementData data = StatementData.of(invoice.customer(), invoice.performances(), plays);
        return renderPlainText(data);
    }

    private String renderPlainText(StatementData data) {
        StringBuilder result = new StringBuilder("청구 내역 (고객명: %s)\n".formatted(data.customer()));

        for (Performance perf : data.performances()) {
            result.append(String.format("%s: $%.2f (%d석)\n", data.playName(perf), usd(data.amountFor(perf)), perf.audience()));
        }

        result.append("총액: $%.2f\n".formatted(usd(data.totalAmount())));
        result.append("적립 포인트: %d점".formatted(data.totalVolumeCredits()));
        return result.toString();
    }

    private String htmlStatement(StatementData data) {
        StringBuilder result = new StringBuilder("<h1>청구 내역 (고객명: %s)</h1>\n".formatted(data.customer()));
        result.append("<table>\n");
        result.append("<tr><th>연극</th><th>좌석 수</th><th>금액</th></tr>");
        for (Performance perf : data.performances()) {
            result.append("<tr><td>%s</td><td>(%d석)</td>".formatted(data.playName(perf), perf.audience()));
            result.append("<td>$%.2f</td></tr>\n".formatted(usd(data.amountFor(perf))));
        }
        result.append("</table>\n");
        result.append("<p>총액: <em>$%.2f</em></p>\n".formatted(usd(data.totalAmount())));
        result.append("<p>적립 포인트: <em>%d점</p>\n".formatted((data.totalVolumeCredits())));
        return result.toString();
    }

    private double usd(int amount) {
        return amount / 100.0;
    }

}
