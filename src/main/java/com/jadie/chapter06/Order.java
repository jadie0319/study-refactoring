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
}
