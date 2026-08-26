package com.project.Inventory.service;

import com.project.Inventory.dto.ProductRequestDTO;
import com.project.Inventory.dto.ProductResponseDTO;
import com.project.Inventory.entity.Product;
import com.project.Inventory.exception.ProductAlreadyExistsException;
import com.project.Inventory.repository.ProductRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.mockito.Mockito.verify;
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

        ProductRequestDTO request = new ProductRequestDTO();

        request.setProductName("Laptop");
        request.setQuantity(10);

        when(productRepository.existsByName("Laptop"))
                .thenReturn(true);


        // Act + Assert

        assertThrows(
                ProductAlreadyExistsException.class,
                () -> productService.addProduct(request)
        );
    }


    @Test
    void shouldAddProductWhenProductDoesNotExist() {

        // Arrange

        ProductRequestDTO request = new ProductRequestDTO();

        request.setProductName("Laptop");
        request.setQuantity(10);


        Product savedProduct = new Product();

        savedProduct.setId(1L);
        savedProduct.setName("Laptop");
        savedProduct.setQuantity(10);


        when(productRepository.existsByName("Laptop"))
                .thenReturn(false);

        when(productRepository.save(org.mockito.ArgumentMatchers.any(Product.class)))
                .thenReturn(savedProduct);


        // Act

        ProductResponseDTO result =
                productService.addProduct(request);


        // Assert

        assertEquals(1L, result.getId());
        assertEquals("Laptop", result.getProductName());
        assertEquals(10, result.getQuantity());
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


        List<Product> products =
                List.of(product1, product2);


        when(productRepository.findAll())
                .thenReturn(products);


        // Act

        List<ProductResponseDTO> result =
                productService.getProduct();


        // Assert

        assertEquals(2, result.size());

        assertEquals(1L, result.get(0).getId());
        assertEquals("Laptop", result.get(0).getProductName());
        assertEquals(10, result.get(0).getQuantity());

        assertEquals(2L, result.get(1).getId());
        assertEquals("Mouse", result.get(1).getProductName());
        assertEquals(20, result.get(1).getQuantity());
    }


    @Test
    void shouldUpdateProduct() {

        // Arrange

        Product product = new Product();

        product.setId(1L);
        product.setName("Laptop");
        product.setQuantity(10);


        ProductRequestDTO request = new ProductRequestDTO();

        request.setProductName("Gaming Laptop");
        request.setQuantity(20);


        when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));

        when(productRepository.save(product))
                .thenReturn(product);


        // Act

        ProductResponseDTO result =
                productService.updateProduct(1L, request);


        // Assert

        assertEquals(1L, result.getId());
        assertEquals("Gaming Laptop", result.getProductName());
        assertEquals(20, result.getQuantity());
    }


    @Test
    void shouldDeleteProduct() {

        // Act

        productService.deleteProduct(1L);


        // Assert

        verify(productRepository)
                .deleteById(1L);
    }
}