package com.example.project31;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @PostMapping
    public ResponseEntity<ProductRequest> createProduct(
            @Valid @RequestBody ProductRequest product) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(product);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getProduct(
            @PathVariable Long id) {

        Map<String, Object> product = Map.of(
                "id", id,
                "name", "Mechanical Keyboard",
                "price", 1500000,
                "categoryId", 1,
                "emailForWarranty", "support@example.com"
        );

        return ResponseEntity.ok(product);
    }

    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> getAllProducts() {
        List<Map<String, Object>> products = List.of(
                Map.of(
                        "id", 1L,
                        "name", "Mechanical Keyboard",
                        "price", 1500000,
                        "categoryId", 1L,
                        "emailForWarranty", "support@example.com"
                ),
                Map.of(
                        "id", 2L,
                        "name", "Gaming Mouse",
                        "price", 750000,
                        "categoryId", 2L,
                        "emailForWarranty", "mouse@example.com"
                ),
                Map.of(
                        "id", 3L,
                        "name", "Gaming Headset",
                        "price", 1200000,
                        "categoryId", 3L,
                        "emailForWarranty", "headset@example.com"
                )
        );

        return ResponseEntity.ok(products);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        return ResponseEntity.noContent().build();
    }
}