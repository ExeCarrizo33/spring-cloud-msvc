package com.exe.spring_cloud.msvc.products_service.controllers;


import com.exe.spring_cloud.msvc.libs_common_service.models.Product;
import com.exe.spring_cloud.msvc.products_service.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public List<Product> findAll() {
        return productService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) throws InterruptedException {

//        if (id.equals(10L)) {
//            throw new IllegalStateException("No se puede cargar el producto");
//        }
        if (id.equals(7L)) {
            TimeUnit.SECONDS.sleep(3L);
        }

        return productService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Product product) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.save(product));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Product product) {
        return productService.findById(id)
                .map(productObj -> {
                    productObj.setName(product.getName());
                    productObj.setPrice(product.getPrice());
                    productObj.setCreatedAt(product.getCreatedAt());
                    return ResponseEntity.status(HttpStatus.CREATED).body(productService.save(productObj));
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        this.productService.deleteById(id);
        return ResponseEntity.noContent().build();
    }


}
