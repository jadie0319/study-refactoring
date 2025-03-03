package com.jadie.chapter07;

public class Order {
    private Integer quantity;
    private Integer item;

    public Order(Integer quantity, Integer item) {
        this.quantity = quantity;
        this.item = item;
    }

    public double calculate() {
        int basePrice = this.quantity * this.item;
        if (basePrice > 1000) {
            return basePrice * 0.95;
        } else {
            return basePrice * 0.98;
        }
    }
}
