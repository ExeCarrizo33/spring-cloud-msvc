package com.exe.spring_cloud.msvc.items_service.clients;


import com.exe.spring_cloud.msvc.items_service.models.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "product-service")
public interface ProductFeignClient {

    @GetMapping
    List<Product> findAll();

    @GetMapping("/{id}")
    Product details(@PathVariable Long id);

    @PostMapping
    Product create(Product product);

    @PutMapping("/{id}")
    Product update(Product product, @PathVariable Long id);

    @DeleteMapping("/{id}")
    void delete(@PathVariable Long id);


}
