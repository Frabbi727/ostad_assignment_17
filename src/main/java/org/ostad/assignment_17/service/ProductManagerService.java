package org.ostad.assignment_17.service;

import org.ostad.assignment_17.exception.ProductNotFoundException;
import org.ostad.assignment_17.model.Product;
import org.ostad.assignment_17.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductManagerService {

    private final ProductRepository productRepository;

    public ProductManagerService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product findProductBySku(String sku) {
        return productRepository.findBySku(sku)
                .orElseThrow(() -> new ProductNotFoundException("Product with SKU '" + sku + "' not found"));
    }

    public Product restockProduct(String sku, int quantityToAdd) {
        Product product = findProductBySku(sku);
        product.setQuantity(product.getQuantity() + quantityToAdd);
        return productRepository.save(product);
    }
}