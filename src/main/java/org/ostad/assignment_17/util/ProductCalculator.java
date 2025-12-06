package org.ostad.assignment_17.util;

public class ProductCalculator {

    public double calculateDiscountedPrice(double originalPrice, double discountRate) {
        if (originalPrice < 0) {
            throw new IllegalArgumentException("Original price cannot be negative");
        }
        if (discountRate < 0 || discountRate > 100) {
            throw new IllegalArgumentException("Discount rate must be between 0 and 100");
        }

        return originalPrice - (originalPrice * discountRate / 100);
    }

    public boolean isQuantitySufficient(int currentQuantity, int requiredQuantity) {
        if (currentQuantity < 0 || requiredQuantity < 0) {
            throw new IllegalArgumentException("Quantities cannot be negative");
        }

        return currentQuantity >= requiredQuantity;
    }
}