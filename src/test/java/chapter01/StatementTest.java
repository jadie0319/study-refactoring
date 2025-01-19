package chapter01;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jadie.chapter01.Invoice;
import com.jadie.chapter01.Play;
import com.jadie.chapter01.Plays;
import com.jadie.chapter01.Statement;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

public class StatementTest {

    private static Plays playMap;
    private static List<Invoice> invoices;

    @BeforeAll
    static void setUp() throws IOException {
        ClassLoader classLoader = StatementTest.class.getClassLoader();
        File playsFile = new File(Objects.requireNonNull(classLoader.getResource("plays.json")).getFile());
        File invoicesFile = new File(Objects.requireNonNull(classLoader.getResource("invoices.json")).getFile());

        ObjectMapper objectMapper = new ObjectMapper();
        playMap = new Plays(objectMapper.readValue(playsFile, new TypeReference<Map<String, Play>>() {}));
        invoices = objectMapper.readValue(invoicesFile, new TypeReference<List<Invoice>>() {});
    }

    @Test
    void statement() throws Exception {
        Statement statement = new Statement();

        String result = statement.statement(invoices.getFirst(), playMap);

        assertThat(result).isEqualTo(
                """
                        청구 내역 (고객명: BigCo)
                        Hamlet: $650.00 (55석)
                        As You Like It: $580.00 (35석)
                        Othello: $500.00 (40석)
                        총액: $1730.00
                        적립 포인트: 47점"""
        );
    }
}
