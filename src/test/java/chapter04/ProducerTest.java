package chapter04;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class ProducerTest {

    @DisplayName("Producer 생성 테스트")
    @Test
    void make() {
        Producer result = new Producer("Byzantium", 10, 9);

        assertThat(result).isNotNull();
    }

    @DisplayName("setProduction 파라미터에 숫자외 문자가 입력되면 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"a","b","ab","1a"})
    void setProduction(String input) {
        Producer producer = new Producer("Byzantium", 10, 9);

        assertThatThrownBy(() -> producer.setProduction(input))
                .isInstanceOf(NumberFormatException.class);
    }
}
