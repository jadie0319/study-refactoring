package com.jadie.chapter06;

public class PriceCalculator {

    public Integer priceOrder(Product product, Integer quantity, ShippingMethod shippingMethod) {
        int basePrice = product.basePrice() * quantity;
        int discount = Math.max(quantity - product.discountThreshold(), 0)
                * product.basePrice() * product.discountRate();
        Integer shippingPerCase = (basePrice > shippingMethod.discountThreshold())
                ? shippingMethod.discountedFee()
                : shippingMethod.feePerCase();
        Integer shippingCost = quantity * shippingPerCase;
        Integer price = basePrice - discount + shippingCost;
        return price;
    }
}
