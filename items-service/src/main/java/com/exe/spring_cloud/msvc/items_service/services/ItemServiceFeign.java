package com.exe.spring_cloud.msvc.items_service.services;

import com.exe.spring_cloud.msvc.items_service.clients.ProductFeignClient;
import com.exe.spring_cloud.msvc.items_service.models.Item;
import com.exe.spring_cloud.msvc.items_service.models.Product;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemServiceFeign implements ItemService {

    private final ProductFeignClient productFeignClient;

    @Override
    public List<Item> findAll() {
        return productFeignClient.findAll()
                .stream()
                .map(product -> new Item(product, new Random().nextInt(10) + 1))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Item> findById(Long id) {
        try {
            Product product = productFeignClient.details(id);
            return Optional.of(new Item(product, new Random().nextInt(10) + 1));
        } catch (FeignException e) {
            return Optional.empty();
        }
    }

    @Override
    public Product save(Product product) {
        return null;
    }

    @Override
    public Product update(Product product, Long id) {
        return null;
    }

    @Override
    public Void delete(Long id) {

        return null;
    }
}
