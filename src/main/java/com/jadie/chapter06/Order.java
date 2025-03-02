package com.jadie.chapter06;

public class Order {
    private Integer amount;
    private Integer quantity;
    private Integer itemPrice;

    public Order(Integer amount) {
        this.amount = amount;
    }

    public Integer getAmount() {
        return amount;
    }

    public Integer getItemPrice() {
        return itemPrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public double calculate(Order order) {
        return basePrice() - quantityDiscount() + shipping();
    }

    public int basePrice() {
        return getQuantity() * getItemPrice();
    }

    public double quantityDiscount() {
        return Math.max(0, getQuantity() - 500) * getItemPrice() * 0.05;
    }

    public double shipping() {
        return Math.min(getQuantity() * getItemPrice() * 0.1, 100);
    }
}
