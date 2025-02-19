package chapter04;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Collections;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class ProducerTest {
    private Producer byzantium;
    private Province province;

    @BeforeEach
    void setUp() {
        province = new Province("Asia", 30, 20, Collections.emptyList());
        byzantium = new Producer(province, "Byzantium", 10, 9);
        province.addProducer(byzantium);
    }

    @DisplayName("Producer 생성 테스트")
    @Test
    void make() {
        Producer result = new Producer("Byzantium", 10, 9);

        assertThat(result).isNotNull();
    }

    @DisplayName("setProduction 인자 값에 숫자외 문자가 입력되면 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"a", "b", "ab", "1a"})
    void setProduction_givenNotNumber(String input) {
        assertThatThrownBy(() -> byzantium.setProduction(input))
                .isInstanceOf(NumberFormatException.class);
    }

    @DisplayName("setProduction 테스트")
    @Test
    void setProduction() {
        assertThat(province.getTotalProduction()).isEqualTo(9);

        byzantium.setProduction("100");

        assertThat(province.getTotalProduction()).isEqualTo(100);
    }

    @DisplayName("setProduction 실행시 province 가 null 이면 예외가 발생한다.")
    @Test
    void setProduction_givenNullProvince() {
        // province 가 null 인 producer 를 못만들게 하면 된다?
        Producer producer = new Producer(null, "Byzantium", 10, 9);

        assertThatThrownBy( () -> producer.setProduction("100"))
                .isInstanceOf(NullPointerException.class);
    }
}
