package chapter04;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ProvinceTest {

    private Province province;

    @BeforeEach
    void setUp() {
        List<Producer> producers = List.of(
                new Producer("Byzantium", 10, 9),
                new Producer("Byzantium", 12, 12),
                new Producer("Byzantium", 10, 6)
        );
        province = new Province("Asia", 30, 20, producers);
    }

    @DisplayName("Province 생성 테스트")
    @Test
    void make() {
        assertThat(province).isNotNull();
    }

    @DisplayName("생산 부족분 계산")
    @Test
    void calculateShortfallTest() {
        assertThat(province.shortfall()).isEqualTo(30);
    }
}
