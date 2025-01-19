package com.jadie.chapter01;

public class ComedyCalculator {

    private final Performance performance;
    private final Play play;

    public ComedyCalculator(Performance aPerformance, Play aPlay) {
        this.performance = aPerformance;
        this.play = aPlay;
    }

    public Integer amount() {
        int result = 0;
        switch (play.type()) {
            case "tragedy" -> {
                result = 40000;
                if (performance.audience() > 30) {
                    result += 1000 * (performance.audience() - 30);
                }
            }
            case "comedy" -> {
                result = 30000;
                if (performance.audience() > 20) {
                    result += 10000 + 500 * (performance.audience() - 20);
                }
                result += 300 * performance.audience();
            }
            default -> throw new RuntimeException("알 수 없는 장르: %s".formatted(play.type()));
        }
        return result;
    }

    public Integer volumeCredits() {
        int result = 0;
        result += Math.max(performance.audience() - 30, 0);
        if ("comedy".equals(play.type())) {
            result += Math.floor(performance.audience() / 5);
        }
        return result;
    }
}
