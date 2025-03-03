package com.jadie.chapter06;

public class PriceCalculator {

    public Integer priceOrder(Product product, Integer quantity, ShippingMethod shippingMethod) {
        int basePrice = product.basePrice() * quantity;
        int discount = Math.max(quantity - product.discountThreshold(), 0)
                * product.basePrice() * product.discountRate();
        Integer price = applyShipping(basePrice,shippingMethod,quantity,discount);
        return price;
    }

    public Integer applyShipping(
            Integer basePrice, ShippingMethod shippingMethod,
            Integer quantity, Integer discount) {
        Integer shippingPerCase = (basePrice > shippingMethod.discountThreshold())
                ? shippingMethod.discountedFee()
                : shippingMethod.feePerCase();
        Integer shippingCost = quantity * shippingPerCase;
        return basePrice - discount + shippingCost;
    }

}
