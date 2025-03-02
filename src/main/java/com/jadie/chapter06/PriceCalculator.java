package com.jadie.chapter06;

public class PriceCalculator {

    public double calculate(Order order) {
        return order.getQuantity() * order.getItemPrice() -
                Math.max(0, order.getQuantity() - 500) * order.getItemPrice() * 0.05 +
                Math.min(order.getQuantity() * order.getItemPrice() * 0.1, 100);
    }
}
