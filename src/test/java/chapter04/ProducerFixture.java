package chapter04;

import java.util.List;

public class ProducerFixture {
    public static Producer BYZANTIUM_PRODUCER = new Producer("Byzantium", 10, 9);
    public static Producer ATTALIA_PRODUCER = new Producer("Attalia", 12, 12);
    public static Producer SINOPE_PRODUCER = new Producer("Sinope", 10, 6);

    public static List<Producer> ASIA_LIST = List.of(BYZANTIUM_PRODUCER, ATTALIA_PRODUCER, SINOPE_PRODUCER);
}
