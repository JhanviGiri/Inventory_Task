package com.project.Inventory.controller;

import com.project.Inventory.dto.ProductRequestDTO;
import com.project.Inventory.dto.ProductResponseDTO;
import com.project.Inventory.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @PostMapping
    public ProductResponseDTO addProduct(@RequestBody ProductRequestDTO request) {
        return service.addProduct(request);
    }



    @GetMapping
    public List<ProductResponseDTO> getProduct() {
        return service.getProduct();
    }


    @PutMapping("/{id}")
    public ProductResponseDTO updateProduct(
            @PathVariable Long id,
            @RequestBody ProductRequestDTO request) {

        return service.updateProduct(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        service.deleteProduct(id);
    }
}