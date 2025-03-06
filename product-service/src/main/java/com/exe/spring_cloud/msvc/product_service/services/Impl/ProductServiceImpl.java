package com.exe.spring_cloud.msvc.product_service.services.Impl;

import com.exe.spring_cloud.msvc.product_service.models.Product;
import com.exe.spring_cloud.msvc.product_service.repositories.ProductRepository;
import com.exe.spring_cloud.msvc.product_service.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return List.of();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Product> findById(Long id) {
        return Optional.empty();
    }
}
