package com.jadie.chapter01;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PerformanceCalculatorTest {

    @DisplayName("hamlet volumeCredits 테스트")
    @Test
    void hamletVolumeCredits() {
        Performance performance = createPerformance("hamlet", 55);
        Play play = createPlay("Hamlet", "tragedy");
        PerformanceCalculator performanceCalculator = PerformanceCalculatorFactory.createCalculator(performance, play);

        Integer result = performanceCalculator.volumeCredits();

        assertThat(result).isEqualTo(25);
    }

    @DisplayName("asYouLikeIt volumeCredits 테스트")
    @Test
    void asYouLikeItVolumeCredits() {
        Performance performance = createPerformance("as-like", 35);
        Play play = createPlay("As You Like It", "comedy");
        PerformanceCalculator performanceCalculator = PerformanceCalculatorFactory.createCalculator(performance, play);

        Integer result = performanceCalculator.volumeCredits();

        assertThat(result).isEqualTo(12);
    }

    @DisplayName("othello volumeCredits 테스트")
    @Test
    void othelloVolumeCredits() {
        Performance performance = createPerformance("othello", 40);
        Play play = createPlay("Othello", "tragedy");
        PerformanceCalculator performanceCalculator = PerformanceCalculatorFactory.createCalculator(performance, play);

        Integer result = performanceCalculator.volumeCredits();

        assertThat(result).isEqualTo(10);
    }

    @DisplayName("hamlet amount 테스트")
    @Test
    void hamletAmount() {
        Performance performance = createPerformance("hamlet", 55);
        Play play = createPlay("Hamlet", "tragedy");
        PerformanceCalculator performanceCalculator = PerformanceCalculatorFactory.createCalculator(performance, play);

        Integer result = performanceCalculator.amount();

        assertThat(result).isEqualTo(65000);
    }

    @DisplayName("asYouLikeIt amount 테스트")
    @Test
    void asYouLikeItAmount() {
        Performance performance = createPerformance("as-like", 35);
        Play play = createPlay("As You Like It", "comedy");
        PerformanceCalculator performanceCalculator = PerformanceCalculatorFactory.createCalculator(performance, play);

        Integer result = performanceCalculator.amount();

        assertThat(result).isEqualTo(58000);
    }

    @DisplayName("othello amount 테스트")
    @Test
    void othelloAmount() {
        Performance performance = createPerformance("othello", 40);
        Play play = createPlay("Othello", "tragedy");
        PerformanceCalculator performanceCalculator = PerformanceCalculatorFactory.createCalculator(performance, play);

        Integer result = performanceCalculator.amount();

        assertThat(result).isEqualTo(50000);
    }

    private Play createPlay(String name, String type) {
        return new Play(name, type);
    }

    private static Performance createPerformance(String playID, int audience) {
        return new Performance(playID, audience);
    }
}
