package com.jadie.chapter06;

public record ShippingMethod(
        Integer discountThreshold,
        Integer discountedFee,
        Integer feePerCase
) {
}
