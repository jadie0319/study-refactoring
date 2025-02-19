package chapter04;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

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
        province.totalProduction += production;
        return new Producer(province, name, cost, production);
    }

    public int shortfall() {
        return this.demand - this.totalProduction;
    }

    public int demandCost() {
        AtomicInteger remainingDemand = new AtomicInteger(this.demand);
        AtomicInteger result = new AtomicInteger();
        this.producers
                .stream()
                .sorted( (a,b) -> a.getCost() - b.getCost())
                .forEach(producer -> {
                    int contribution = Math.min(remainingDemand.get(), producer.getProduction());
                    remainingDemand.addAndGet(-contribution);
                    result.addAndGet(contribution * producer.getCost());
                });

        return result.get();
    }

    public int getTotalProduction() {
        return totalProduction;
    }

    public int satisfiedDemand() {
        return Math.min(this.demand, this.totalProduction);
    }

    public int demandValue() {
        return satisfiedDemand() * this.price;
    }

    public int profit() {
        return demandValue() - demandCost();
    }
}
