package com.jadie.chapter01;

import chapter01.StatementTest;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class PerformanceCalculatorTest {

    private static Plays plays;
    private static List<Invoice> invoices;

    @BeforeAll
    static void setUp() throws IOException {
        ClassLoader classLoader = StatementTest.class.getClassLoader();
        File playsFile = new File(Objects.requireNonNull(classLoader.getResource("plays.json")).getFile());
        File invoicesFile = new File(Objects.requireNonNull(classLoader.getResource("invoices.json")).getFile());

        ObjectMapper objectMapper = new ObjectMapper();
        plays = new Plays(objectMapper.readValue(playsFile, new TypeReference<Map<String, Play>>() {}));
        invoices = objectMapper.readValue(invoicesFile, new TypeReference<List<Invoice>>() {});
    }

    @DisplayName("hamlet volumeCredits 테스트")
    @Test
    void hamletVolumeCredits() {
        Performance performance = new Performance("hamlet", 55);
        Play play = new Play("Hamlet", "tragedy");
        PerformanceCalculator performanceCalculator = PerformanceCalculatorFactory.createCalculator(performance, play);

        Integer result = performanceCalculator.volumeCredits();

        assertThat(result).isEqualTo(25);
    }

    @DisplayName("asYouLikeIt volumeCredits 테스트")
    @Test
    void asYouLikeItVolumeCredits() {
        Performance performance = new Performance("as-like", 35);
        Play play = new Play("As You Like It", "comedy");
        PerformanceCalculator performanceCalculator = PerformanceCalculatorFactory.createCalculator(performance, play);

        Integer result = performanceCalculator.volumeCredits();

        assertThat(result).isEqualTo(12);
    }

    @DisplayName("othello volumeCredits 테스트")
    @Test
    void othelloVolumeCredits() {
        Performance performance = new Performance("othello", 40);
        Play play = new Play("Othello", "tragedy");
        PerformanceCalculator performanceCalculator = PerformanceCalculatorFactory.createCalculator(performance, play);

        Integer result = performanceCalculator.volumeCredits();

        assertThat(result).isEqualTo(10);
    }

    @DisplayName("hamlet amount 테스트")
    @Test
    void hamletAmount() {
        Performance performance = new Performance("hamlet", 55);
        Play play = new Play("Hamlet", "tragedy");
        PerformanceCalculator performanceCalculator = PerformanceCalculatorFactory.createCalculator(performance, play);

        Integer result = performanceCalculator.amount();

        assertThat(result).isEqualTo(65000);
    }

    @DisplayName("asYouLikeIt amount 테스트")
    @Test
    void asYouLikeItAmount() {
        Performance performance = new Performance("as-like", 35);
        Play play = new Play("As You Like It", "comedy");
        PerformanceCalculator performanceCalculator = PerformanceCalculatorFactory.createCalculator(performance, play);

        Integer result = performanceCalculator.amount();

        assertThat(result).isEqualTo(58000);
    }

    @DisplayName("othello amount 테스트")
    @Test
    void othelloAmount() {
        Performance performance = new Performance("othello", 40);
        Play play = new Play("Othello", "tragedy");
        PerformanceCalculator performanceCalculator = PerformanceCalculatorFactory.createCalculator(performance, play);

        Integer result = performanceCalculator.amount();

        assertThat(result).isEqualTo(50000);
    }

}
