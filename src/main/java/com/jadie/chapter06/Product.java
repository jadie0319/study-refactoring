package com.jadie.chapter06;

public record Product(
        Integer basePrice,
        Integer discountThreshold,
        Integer discountRate
) {
}
