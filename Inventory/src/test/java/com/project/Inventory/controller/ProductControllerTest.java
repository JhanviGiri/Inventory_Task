package com.project.Inventory.controller;

import com.project.Inventory.dto.ProductRequestDTO;
import com.project.Inventory.dto.ProductResponseDTO;
import com.project.Inventory.service.ProductService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {

        mockMvc = MockMvcBuilders
                .standaloneSetup(productController)
                .build();
    }


    @Test
    void shouldAddProduct() throws Exception {

        ProductResponseDTO response = new ProductResponseDTO(
                1L,
                "Laptop",
                10
        );

        when(productService.addProduct(any(ProductRequestDTO.class)))
                .thenReturn(response);

        mockMvc.perform(
                        post("/api/products")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                {
                                    "productName": "Laptop",
                                    "quantity": 10
                                }
                                """)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.productName").value("Laptop"))
                .andExpect(jsonPath("$.quantity").value(10));
    }


    @Test
    void shouldGetProducts() throws Exception {

        ProductResponseDTO product1 = new ProductResponseDTO(
                1L,
                "Laptop",
                10
        );

        ProductResponseDTO product2 = new ProductResponseDTO(
                2L,
                "Mouse",
                20
        );

        when(productService.getProduct())
                .thenReturn(List.of(product1, product2));

        mockMvc.perform(
                        get("/api/products")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].productName").value("Laptop"))
                .andExpect(jsonPath("$[0].quantity").value(10))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].productName").value("Mouse"))
                .andExpect(jsonPath("$[1].quantity").value(20));
    }


    @Test
    void shouldUpdateProduct() throws Exception {

        ProductResponseDTO response = new ProductResponseDTO(
                1L,
                "Gaming Laptop",
                20
        );

        ProductRequestDTO request = new ProductRequestDTO();

        request.setProductName("Gaming Laptop");
        request.setQuantity(20);

        when(productService.updateProduct(
                any(Long.class),
                any(ProductRequestDTO.class)
        ))
                .thenReturn(response);

        mockMvc.perform(
                        put("/api/products/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                {
                                    "productName": "Gaming Laptop",
                                    "quantity": 20
                                }
                                """)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.productName").value("Gaming Laptop"))
                .andExpect(jsonPath("$.quantity").value(20));
    }


    @Test
    void shouldDeleteProduct() throws Exception {

        doNothing()
                .when(productService)
                .deleteProduct(1L);

        mockMvc.perform(
                        delete("/api/products/1")
                )
                .andExpect(status().isOk());
    }
}