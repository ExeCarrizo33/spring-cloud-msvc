package com.exe.spring_cloud.msvc.items_service.services;

import com.exe.spring_cloud.msvc.items_service.clients.ProductFeingClient;
import com.exe.spring_cloud.msvc.items_service.models.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class ItemServiceFeing implements ItemService {

    private final ProductFeingClient productFeingClient;

    @Override
    public List<Item> findAll() {
        return productFeingClient.findAll().stream()
                .map(product -> new Item(product, new Random().nextInt(10)+1))
                .toList();
    }

    @Override
    public Optional<Item> findById(Long id) {
        return Optional.of(new Item(productFeingClient.details(id), new Random().nextInt(10)+1));
    }
}
