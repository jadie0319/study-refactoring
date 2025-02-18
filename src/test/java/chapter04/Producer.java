package chapter04;

import java.util.Optional;

public class Producer {
    private Province province;
    private int cost;
    private String name;
    private int production;

    public Producer(Province province, String name, int cost, int production) {
        this.province = province;
        this.cost = cost;
        this.name = name;
        this.production = Optional.of(production).orElse(0);
    }
}
