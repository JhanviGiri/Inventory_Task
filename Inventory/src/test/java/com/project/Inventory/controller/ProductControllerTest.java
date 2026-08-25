package com.project.Inventory.controller;

import com.project.Inventory.entity.Product;
import com.project.Inventory.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    private MockMvc mockMvc;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(productController)
                .build();
    }

    @Test
    void shouldAddProduct() throws Exception {

        Product product = new Product();
        product.setId(1L);
        product.setName("Laptop");
        product.setQuantity(10);

        when(productService.addProduct(org.mockito.ArgumentMatchers.any(Product.class)))
                .thenReturn(product);

        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "id": 1,
                                    "name": "Laptop",
                                    "quantity": 10
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Laptop"))
                .andExpect(jsonPath("$.quantity").value(10));
    }

    @Test
    void shouldGetProducts() throws Exception {

        Product product = new Product();
        product.setId(1L);
        product.setName("Laptop");
        product.setQuantity(10);

        when(productService.getProduct())
                .thenReturn(List.of(product));

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Laptop"))
                .andExpect(jsonPath("$[0].quantity").value(10));
    }

    @Test
    void shouldUpdateQuantity() throws Exception {

        Product product = new Product();
        product.setId(1L);
        product.setName("Laptop");
        product.setQuantity(20);

        when(productService.updateQuantity(1L, 20))
                .thenReturn(product);

        mockMvc.perform(put("/api/products/1")
                        .param("quantity", "20"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.quantity").value(20));
    }

    @Test
    void shouldDeleteProduct() throws Exception {

        doNothing()
                .when(productService)
                .deleteProduct(1L);

        mockMvc.perform(delete("/api/products/1"))
                .andExpect(status().isOk());
    }
}