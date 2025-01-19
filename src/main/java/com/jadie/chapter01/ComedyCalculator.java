package com.jadie.chapter01;

public class ComedyCalculator implements PerformanceCalculator{

    private final Performance performance;
    private final Play play;

    public ComedyCalculator(Performance aPerformance, Play aPlay) {
        this.performance = aPerformance;
        this.play = aPlay;
    }

    public Integer amount() {
        int result = 30000;
        if (performance.audience() > 20) {
            result += 10000 + 500 * (performance.audience() - 20);
        }
        result += 300 * performance.audience();
        return result;
    }

    public Integer volumeCredits() {
        int result = 0;
        result += Math.max(performance.audience() - 30, 0);
        result += Math.floor(performance.audience() / 5);
        return result;
    }
}
