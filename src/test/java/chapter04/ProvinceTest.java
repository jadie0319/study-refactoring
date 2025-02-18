package chapter04;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static chapter04.ProvinceFixture.ASIA_PROVINCE;
import static org.assertj.core.api.Assertions.assertThat;

public class ProvinceTest {

    private Province province;

    @BeforeEach
    void setUp() {
        province = ASIA_PROVINCE;
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
