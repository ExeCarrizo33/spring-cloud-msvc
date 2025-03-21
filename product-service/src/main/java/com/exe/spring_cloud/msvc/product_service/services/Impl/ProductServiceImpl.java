package com.exe.spring_cloud.msvc.product_service.services.Impl;

import com.exe.spring_cloud.msvc.product_service.models.Product;
import com.exe.spring_cloud.msvc.product_service.repositories.ProductRepository;
import com.exe.spring_cloud.msvc.product_service.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final Environment environment;

    @Override
    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return ((List<Product>) productRepository.findAll())
                .stream()
                .map(product -> {
                    product.setPort(Integer.parseInt(environment.getProperty("local.server.port")));
                    return product;
                }).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id).map(product -> {
            product.setPort(Integer.parseInt(environment.getProperty("local.server.port")));
            return product;
        });
    }

    @Transactional
    @Override
    public Product save(Product product) {
        return this.productRepository.save(product);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        this.productRepository.deleteById(id);
    }
}
