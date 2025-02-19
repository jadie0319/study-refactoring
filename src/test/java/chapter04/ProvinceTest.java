package chapter04;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;

import static chapter04.ProducerFixture.ASIA_PRODUCER_LIST;
import static chapter04.ProducerFixture.BYZANTIUM_PRODUCER;
import static org.assertj.core.api.Assertions.assertThat;

public class ProvinceTest {

    private Province asiaProvince;

    @BeforeEach
    void setUp() {
        asiaProvince = new Province("Asia", 30, 20, ASIA_PRODUCER_LIST);
    }

    @DisplayName("Province 생성 테스트")
    @Test
    void make() {
        Province result = new Province("Asia", 30, 20, Collections.emptyList());

        assertThat(result).isNotNull();
    }

    @DisplayName("Producer 추가 테스트")
    @Test
    void addProducer() {
        Province province = new Province("Asia", 30, 20, new ArrayList<>());

        province.addProducer(BYZANTIUM_PRODUCER);

        assertThat(province.getTotalProduction()).isEqualTo(9);
    }

    @DisplayName("totalProduction 테스트")
    @Test
    void totalProduction() {
        assertThat(asiaProvince.getTotalProduction()).isEqualTo(27);
    }

    @DisplayName("생산 부족분 계산")
    @Test
    void calculateShortfall() {
        assertThat(asiaProvince.shortfall()).isEqualTo(3);
    }

    @DisplayName("수익(demandCost) 계산")
    @Test
    void demandCost() {
        assertThat(asiaProvince.demandCost()).isEqualTo(294);
    }

    @DisplayName("satisfiedDemand 계산")
    @Test
    void satisfiedDemandTest() {
        assertThat(asiaProvince.satisfiedDemand()).isEqualTo(27);
    }

    @DisplayName("demandValue 계산")
    @Test
    void demandValue() {
        assertThat(asiaProvince.demandValue()).isEqualTo(540);
    }

    @DisplayName("profit 계산")
    @Test
    void profit() {
        assertThat(asiaProvince.profit()).isEqualTo(246);
    }
}
