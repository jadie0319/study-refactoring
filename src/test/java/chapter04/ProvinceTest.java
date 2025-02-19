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
        assertThat(province.getTotalProduction()).isEqualTo(27);
    }

    @DisplayName("생산 부족분 계산")
    @Test
    void calculateShortfallTest() {
        assertThat(province.shortfall()).isEqualTo(3);
    }

    @DisplayName("수익(demandCost) 계산")
    @Test
    void demandCostTest() {
        assertThat(province.demandCost()).isEqualTo(294);
    }

    @DisplayName("satisfiedDemand 계산")
    @Test
    void satisfiedDemandTest() {
        assertThat(province.satisfiedDemand()).isEqualTo(27);
    }

    @DisplayName("demandValue 계산")
    @Test
    void demandValueTest() {
        assertThat(province.demandValue()).isEqualTo(540);
    }
}
