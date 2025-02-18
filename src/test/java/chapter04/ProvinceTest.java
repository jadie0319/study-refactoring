package chapter04;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ProvinceTest {

    @DisplayName("Province 생성 테스트")
    @Test
    void make() {
        List<Producer> producers = List.of(
                new Producer("Byzantium", 10, 9),
                new Producer("Byzantium", 12, 12),
                new Producer("Byzantium", 10, 6)
        );
        Province province = new Province("Asia", 30, 20, producers);

        assertThat(province).isNotNull();
    }

}
