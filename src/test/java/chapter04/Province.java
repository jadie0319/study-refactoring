package chapter04;

import java.util.List;

public class Province {
    private String name;
    private List<Producer> producers;
    private int totalProduction;
    private int demand;
    private int price;

    public Province(String name, int demand, int price, List<Producer> producers) {
        this.name = name;
        this.demand = demand;
        this.price = price;
        this.totalProduction = 0;
        this.producers = producers.stream()
                .map(producer -> makeProducer(
                                this,
                                producer.getName(),
                                producer.getCost(),
                                producer.getProduction()
                        )
                ).toList();
    }

    private Producer makeProducer(Province province, String name, int cost, int production) {
        return new Producer(province, name, cost, production);
    }

    public int shortfall() {
        return this.demand - this.totalProduction;
    }

    public int demandCost() {
        return 12;
    }
}
