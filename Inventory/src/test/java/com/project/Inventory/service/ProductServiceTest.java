package com.project.Inventory.service;

import com.project.Inventory.entity.Product;
import com.project.Inventory.exception.ProductAlreadyExistsException;
import com.project.Inventory.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void shouldThrowExceptionWhenProductAlreadyExists() {

        // Arrange
        Product product = new Product();
        product.setId(1L);
        product.setName("Laptop");
        product.setQuantity(10);

        when(productRepository.existsById(1L))
                .thenReturn(true);

        // Act + Assert
        assertThrows(
                ProductAlreadyExistsException.class,
                () -> productService.addProduct(product)
        );
    }

    @Test
    void shouldAddProductWhenProductDoesNotExist() {

        // Arrange
        Product product = new Product();
        product.setId(1L);
        product.setName("Laptop");
        product.setQuantity(10);

        when(productRepository.existsById(1L))
                .thenReturn(false);

        when(productRepository.save(product))
                .thenReturn(product);

        // Act
        Product result = productService.addProduct(product);

        // Assert
        assertEquals(product, result);
    }

    @Test
    void shouldReturnAllProducts() {

        // Arrange
        Product product1 = new Product();
        product1.setId(1L);
        product1.setName("Laptop");
        product1.setQuantity(10);

        Product product2 = new Product();
        product2.setId(2L);
        product2.setName("Mouse");
        product2.setQuantity(20);

        List<Product> products = List.of(product1, product2);

        when(productRepository.findAll())
                .thenReturn(products);

        // Act
        List<Product> result = productService.getProduct();

        // Assert
        assertEquals(products, result);
    }

    @Test
    void shouldUpdateProductQuantity() {

        // Arrange
        Product product = new Product();
        product.setId(1L);
        product.setName("Laptop");
        product.setQuantity(10);

        when(productRepository.findById(1L))
                .thenReturn(java.util.Optional.of(product));

        when(productRepository.save(product))
                .thenReturn(product);

        // Act
        Product result = productService.updateQuantity(1L, 20);

        // Assert
        assertEquals(20, result.getQuantity());
    }

    @Test
    void shouldDeleteProduct() {

        productService.deleteProduct(1L);

        verify(productRepository).deleteById(1L);
    }
}
