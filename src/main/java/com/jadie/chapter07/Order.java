package com.jadie.chapter07;

public class Order {
    private Integer quantity;
    private Integer item;

    public Order(Integer quantity, Integer item) {
        this.quantity = quantity;
        this.item = item;
    }

    public double calculate() {
        if (basePrice() > 1000) {
            return basePrice() * 0.95;
        } else {
            return basePrice() * 0.98;
        }
    }

    public double price() {
        return basePrice() * discountFactor();
    }

    public Integer basePrice() {return quantity * item;}
    public double discountFactor() {
        double discountFactor = 0.98;
        if (basePrice() > 1000) {
            discountFactor -= 0.03;
        }
        return discountFactor;
    }
}
