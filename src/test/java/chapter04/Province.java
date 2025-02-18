package chapter04;

import java.util.List;

public class Province {
    private String name;
    private List<Producer> producers;
    private int totalProduction;
    private int demand;
    private int price;

    public Province(String name, int demand, int price, int cost, int production) {
        this.name = name;
        this.totalProduction = 0;
        this.demand = demand;
        this.price = price;
        this.producers = makeProduct(name, cost, production);
    }

    private List<Producer> makeProduct(String name, int cost, Integer production) {
        return List.of(new Producer(this, name, cost, production));
    }
}
