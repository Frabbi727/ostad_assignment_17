package org.ostad.assignment_17.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductCalculatorTest {

    private ProductCalculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new ProductCalculator();
    }

    @Test
    @DisplayName("Calculate discounted price with 0% discount")
    void testCalculateDiscountedPrice_ZeroDiscount() {
        double originalPrice = 100.0;
        double discountRate = 0.0;

        double result = calculator.calculateDiscountedPrice(originalPrice, discountRate);

        assertEquals(100.0, result, 0.01);
    }

    @Test
    @DisplayName("Calculate discounted price with 50% discount")
    void testCalculateDiscountedPrice_FiftyPercent() {
        double originalPrice = 100.0;
        double discountRate = 50.0;

        double result = calculator.calculateDiscountedPrice(originalPrice, discountRate);

        assertEquals(50.0, result, 0.01);
    }

    @Test
    @DisplayName("Calculate discounted price with 100% discount (full discount)")
    void testCalculateDiscountedPrice_FullDiscount() {
        double originalPrice = 100.0;
        double discountRate = 100.0;

        double result = calculator.calculateDiscountedPrice(originalPrice, discountRate);

        assertEquals(0.0, result, 0.01);
    }

    @Test
    @DisplayName("Calculate discounted price with 25% discount")
    void testCalculateDiscountedPrice_TwentyFivePercent() {
        double originalPrice = 200.0;
        double discountRate = 25.0;

        double result = calculator.calculateDiscountedPrice(originalPrice, discountRate);

        assertEquals(150.0, result, 0.01);
    }

    @Test
    @DisplayName("Calculate discounted price throws exception for negative price")
    void testCalculateDiscountedPrice_NegativePrice() {
        double originalPrice = -100.0;
        double discountRate = 10.0;

        assertThrows(IllegalArgumentException.class, () -> {
            calculator.calculateDiscountedPrice(originalPrice, discountRate);
        });
    }

    @Test
    @DisplayName("Calculate discounted price throws exception for discount rate > 100")
    void testCalculateDiscountedPrice_DiscountRateAbove100() {
        double originalPrice = 100.0;
        double discountRate = 150.0;

        assertThrows(IllegalArgumentException.class, () -> {
            calculator.calculateDiscountedPrice(originalPrice, discountRate);
        });
    }

    @Test
    @DisplayName("Calculate discounted price throws exception for negative discount rate")
    void testCalculateDiscountedPrice_NegativeDiscountRate() {
        double originalPrice = 100.0;
        double discountRate = -10.0;

        assertThrows(IllegalArgumentException.class, () -> {
            calculator.calculateDiscountedPrice(originalPrice, discountRate);
        });
    }

    @Test
    @DisplayName("Check quantity is sufficient when stock equals required")
    void testIsQuantitySufficient_ExactMatch() {
        int currentQuantity = 10;
        int requiredQuantity = 10;

        boolean result = calculator.isQuantitySufficient(currentQuantity, requiredQuantity);

        assertTrue(result);
    }

    @Test
    @DisplayName("Check quantity is sufficient when stock exceeds required")
    void testIsQuantitySufficient_ExcessStock() {
        int currentQuantity = 20;
        int requiredQuantity = 10;

        boolean result = calculator.isQuantitySufficient(currentQuantity, requiredQuantity);

        assertTrue(result);
    }

    @Test
    @DisplayName("Check quantity is insufficient when stock is less than required")
    void testIsQuantitySufficient_InsufficientStock() {
        int currentQuantity = 5;
        int requiredQuantity = 10;

        boolean result = calculator.isQuantitySufficient(currentQuantity, requiredQuantity);

        assertFalse(result);
    }

    @Test
    @DisplayName("Check quantity is sufficient with zero stock and zero required")
    void testIsQuantitySufficient_ZeroQuantities() {
        int currentQuantity = 0;
        int requiredQuantity = 0;

        boolean result = calculator.isQuantitySufficient(currentQuantity, requiredQuantity);

        assertTrue(result);
    }

    @Test
    @DisplayName("Check quantity throws exception for negative current quantity")
    void testIsQuantitySufficient_NegativeCurrentQuantity() {
        int currentQuantity = -5;
        int requiredQuantity = 10;

        assertThrows(IllegalArgumentException.class, () -> {
            calculator.isQuantitySufficient(currentQuantity, requiredQuantity);
        });
    }

    @Test
    @DisplayName("Check quantity throws exception for negative required quantity")
    void testIsQuantitySufficient_NegativeRequiredQuantity() {
        int currentQuantity = 10;
        int requiredQuantity = -5;

        assertThrows(IllegalArgumentException.class, () -> {
            calculator.isQuantitySufficient(currentQuantity, requiredQuantity);
        });
    }
}