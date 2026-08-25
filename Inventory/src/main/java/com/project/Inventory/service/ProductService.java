package com.project.Inventory.service;

import com.project.Inventory.entity.Product;
import com.project.Inventory.exception.ProductAlreadyExistsException;
import com.project.Inventory.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product addProduct(Product product) {

        if (productRepository.existsById(product.getId())) {
            throw new ProductAlreadyExistsException("Product Already Exist");
        }

        return productRepository.save(product);
    }

    public List<Product> getProduct() {
        return productRepository.findAll();
    }

    public Product updateQuantity(Long id, int quantity) {

        Product product = productRepository.findById(id).orElseThrow();

        product.setQuantity(quantity);

        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}