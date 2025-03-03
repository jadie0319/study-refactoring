package com.jadie.chapter06;

public class PriceCalculator {

    public Integer priceOrder(
            Product product, Integer quantity,
            ShippingMethod shippingMethod)
    {
        PriceData priceData = calculatePricingData(product, quantity);
        return applyShipping(priceData,shippingMethod);
    }

    public PriceData calculatePricingData(Product product, Integer quantity) {
        int basePrice = product.basePrice() * quantity;
        int discount = Math.max(quantity - product.discountThreshold(), 0)
                * product.basePrice() * product.discountRate();
        return new PriceData(basePrice, quantity, discount);
    }

    public Integer applyShipping(
            PriceData priceData, ShippingMethod shippingMethod)
    {
        Integer shippingPerCase = (priceData.basePrice() > shippingMethod.discountThreshold())
                ? shippingMethod.discountedFee()
                : shippingMethod.feePerCase();
        Integer shippingCost = priceData.quantity() * shippingPerCase;
        return priceData.basePrice() - priceData.discount() + shippingCost;
    }

}
