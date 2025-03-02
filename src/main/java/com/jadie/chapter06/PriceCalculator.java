package com.jadie.chapter06;

public class PriceCalculator {

    public double calculate(Order order) {
        int basePrice = order.getQuantity() * order.getItemPrice();
        double quantityDiscount = Math.max(0, order.getQuantity() - 500) * order.getItemPrice() * 0.05;
        double shipping = Math.min(order.getQuantity() * order.getItemPrice() * 0.1, 100);
        return basePrice - quantityDiscount + shipping;
    }
}
