package chapter04;

import java.util.ArrayList;
import java.util.List;

public class Province {
    private String name;
    private List<Producer> producers;
    private Integer totalProduction;
    private Integer demand;
    private Integer price;

    public Province(String name, Integer demand, Integer price, Integer cost, Integer production) {
        this.name = name;
        this.totalProduction = 0;
        this.demand = demand;
        this.price = price;
        this.producers = makeProduct(name, cost, production, new ArrayList<Producer>());
    }

    private List<Producer> makeProduct(String name, Integer cost, Integer production, List<Producer> producers) {
        producers.add(new Producer(this, name, cost, production));
        return producers;
    }
}
