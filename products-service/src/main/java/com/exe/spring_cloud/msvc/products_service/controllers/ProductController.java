package com.exe.spring_cloud.msvc.products_service.controllers;


import com.exe.spring_cloud.msvc.libs_common_service.models.Product;
import com.exe.spring_cloud.msvc.products_service.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final Logger logger = LoggerFactory.getLogger(ProductController.class);


    @GetMapping
    public List<Product> findAll(@RequestHeader(value = "message-request", required = false) String message) {
        logger.info("message-request: {}", message);
        logger.info("Ingresando al metodo del controller ProductController:findAll");
        return productService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) throws InterruptedException {

        logger.info("Ingresando al metodo del controller ProductController:details");


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

        logger.info("Ingresando al metodo del controller ProductController:create, creando: {},", product);
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.save(product));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Product product) {

        logger.info("Ingresando al metodo del controller ProductController:update, actualizando: {},", product);
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

        logger.info("Ingresando al metodo del controller ProductController:delete, eliminando el producto con id: {},", id);
        this.productService.deleteById(id);
        return ResponseEntity.noContent().build();
    }


}
