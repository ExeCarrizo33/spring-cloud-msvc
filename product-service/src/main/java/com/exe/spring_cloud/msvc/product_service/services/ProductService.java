package com.exe.spring_cloud.msvc.product_service.services;

import com.exe.spring_cloud.msvc.product_service.models.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<Product> findAll();

    Optional<Product> findById(Long id);

    Product save(Product product);

    void deleteById(Long id);

}
