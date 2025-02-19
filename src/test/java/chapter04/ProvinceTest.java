package chapter04;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static chapter04.ProducerFixture.ASIA_LIST;
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
        Province result = new Province("Asia", 30, 20, Collections.emptyList());

        assertThat(result).isNotNull();
    }

    @DisplayName("totalProduction 테스트")
    @Test
    void totalProduction() {
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

    @DisplayName("profit 계산")
    @Test
    void profitTest() {
        assertThat(province.profit()).isEqualTo(246);
    }
}
