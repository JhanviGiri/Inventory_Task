package com.project.Inventory.controller;

import com.project.Inventory.entity.Product;
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
    public Product addProduct(@RequestBody Product product) {
        return service.addProduct(product);
    }

    @GetMapping
    public List<Product> getProduct() {
        return service.getProduct();
    }

    @PutMapping("/{id}")
    public Product updateQuantity(
            @PathVariable Long id,
            @RequestParam int quantity) {

        return service.updateQuantity(id, quantity);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        service.deleteProduct(id);
    }
}