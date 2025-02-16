package chapter04;

public class Producer {
    private Province province;
    private Integer cost;
    private String name;
    private Integer production;

    public Producer(Province province, String name, Integer cost, Integer production) {
        this.province = province;
        this.cost = cost;
        this.name = name;
        this.production = production;
    }
}
