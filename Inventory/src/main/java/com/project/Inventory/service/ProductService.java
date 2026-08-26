package com.project.Inventory.service;

import com.project.Inventory.dto.ProductRequestDTO;
import com.project.Inventory.dto.ProductResponseDTO;
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

    public ProductResponseDTO addProduct(ProductRequestDTO request) {

        if (productRepository.existsByName(request.getProductName())) {
            throw new ProductAlreadyExistsException("Product Already Exist");
        }

        Product product = new Product();
        product.setName(request.getProductName());
        product.setQuantity(request.getQuantity());

        Product savedProduct = productRepository.save(product);

        return new ProductResponseDTO(
                savedProduct.getId(),
                savedProduct.getName(),
                savedProduct.getQuantity()
        ) ;
    }


    public List<ProductResponseDTO> getProduct() {

        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(product -> new ProductRequestDTO(
                    product.getId(),
                    product.getName(),
                    product.getQuantity()
                ));
    }

    public ProductResponseDTO updateQuantity(Long id, int quantity) {

        Product product = productRepository.findById(id).orElseThrow();

        product.setQuantity(quantity);

        Product updatedProduct = productRepository.save(product);
        return new ProductResponseDTO(
                updatedProduct.getId(),
                updatedProduct.getName(),
                updatedProduct.getQuantity()
        );
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}