package com.jadie.chapter01;

public class TragedyCalculator implements PerformanceCalculator{

    private final Performance performance;
    private final Play play;

    public TragedyCalculator(Performance aPerformance, Play aPlay) {
        this.performance = aPerformance;
        this.play = aPlay;
    }

    public Integer amount() {
        int result = 40000;
        if (performance.audience() > 30) {
            result += 1000 * (performance.audience() - 30);
        }
        return result;
    }

    public Integer volumeCredits() {
        int result = 0;
        result += Math.max(performance.audience() - 30, 0);
        return result;
    }
}
