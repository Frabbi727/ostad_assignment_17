package org.ostad.assignment_17.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.ostad.assignment_17.exception.ProductNotFoundException;
import org.ostad.assignment_17.model.Product;
import org.ostad.assignment_17.repository.ProductRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductManagerServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductManagerService productManagerService;

    private Product sampleProduct;

    @BeforeEach
    void setUp() {
        sampleProduct = new Product("SKU-001", "Laptop", "High-end gaming laptop", 1500.0, 10);
        sampleProduct.setId(1L);
    }

    @Test
    @DisplayName("Successfully find product by SKU")
    void testFindProductBySku_Success() {
        String sku = "SKU-001";
        when(productRepository.findBySku(sku)).thenReturn(Optional.of(sampleProduct));

        Product result = productManagerService.findProductBySku(sku);

        assertNotNull(result);
        assertEquals("SKU-001", result.getSku());
        assertEquals("Laptop", result.getName());
        assertEquals(1500.0, result.getPrice());
        assertEquals(10, result.getQuantity());
        verify(productRepository, times(1)).findBySku(sku);
    }

    @Test
    @DisplayName("Throw ProductNotFoundException when SKU not found")
    void testFindProductBySku_NotFound() {
        String sku = "INVALID-SKU";
        when(productRepository.findBySku(sku)).thenReturn(Optional.empty());

        ProductNotFoundException exception = assertThrows(ProductNotFoundException.class, () -> {
            productManagerService.findProductBySku(sku);
        });

        assertTrue(exception.getMessage().contains("INVALID-SKU"));
        assertTrue(exception.getMessage().contains("not found"));
        verify(productRepository, times(1)).findBySku(sku);
    }

    @Test
    @DisplayName("Successfully restock product")
    void testRestockProduct_Success() {
        String sku = "SKU-001";
        int quantityToAdd = 5;
        int expectedQuantity = 15;

        when(productRepository.findBySku(sku)).thenReturn(Optional.of(sampleProduct));
        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Product result = productManagerService.restockProduct(sku, quantityToAdd);

        assertNotNull(result);
        assertEquals(expectedQuantity, result.getQuantity());
        verify(productRepository, times(1)).findBySku(sku);
        verify(productRepository, times(1)).save(sampleProduct);
    }

    @Test
    @DisplayName("Restock updates quantity correctly")
    void testRestockProduct_QuantityUpdate() {
        String sku = "SKU-001";
        int quantityToAdd = 20;

        when(productRepository.findBySku(sku)).thenReturn(Optional.of(sampleProduct));
        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> {
            Product savedProduct = invocation.getArgument(0);
            assertEquals(30, savedProduct.getQuantity());
            return savedProduct;
        });

        Product result = productManagerService.restockProduct(sku, quantityToAdd);

        assertEquals(30, result.getQuantity());
        verify(productRepository, times(1)).save(sampleProduct);
    }

    @Test
    @DisplayName("Throw ProductNotFoundException when restocking non-existent product")
    void testRestockProduct_NotFound() {
        String sku = "INVALID-SKU";
        int quantityToAdd = 5;

        when(productRepository.findBySku(sku)).thenReturn(Optional.empty());

        ProductNotFoundException exception = assertThrows(ProductNotFoundException.class, () -> {
            productManagerService.restockProduct(sku, quantityToAdd);
        });

        assertTrue(exception.getMessage().contains("INVALID-SKU"));
        assertTrue(exception.getMessage().contains("not found"));
        verify(productRepository, times(1)).findBySku(sku);
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    @DisplayName("Verify repository save is called exactly once during restock")
    void testRestockProduct_SaveCalledOnce() {
        String sku = "SKU-001";
        int quantityToAdd = 10;

        when(productRepository.findBySku(sku)).thenReturn(Optional.of(sampleProduct));
        when(productRepository.save(any(Product.class))).thenReturn(sampleProduct);

        productManagerService.restockProduct(sku, quantityToAdd);

        verify(productRepository, times(1)).save(argThat(product ->
            product.getSku().equals("SKU-001") && product.getQuantity() == 20
        ));
    }

    @Test
    @DisplayName("Restock with zero quantity")
    void testRestockProduct_ZeroQuantity() {
        String sku = "SKU-001";
        int quantityToAdd = 0;

        when(productRepository.findBySku(sku)).thenReturn(Optional.of(sampleProduct));
        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Product result = productManagerService.restockProduct(sku, quantityToAdd);

        assertEquals(10, result.getQuantity());
        verify(productRepository, times(1)).save(sampleProduct);
    }
}