package com.exe.spring_cloud.msvc.items_service.services;

import com.exe.spring_cloud.msvc.items_service.models.Item;
import com.exe.spring_cloud.msvc.items_service.models.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;

@Primary
@Service
@RequiredArgsConstructor
public class ItemServiceWebClient implements ItemService {

    private final WebClient.Builder webClientBuilder;


    @Override
    public List<Item> findAll() {

        return this.webClientBuilder.build()
                .get()
                .uri("http://product-service")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(Product.class)
                .map(product -> new Item(product, new Random().nextInt(10)+1))
                .collectList()
                .block();
    }

    @Override
    public Optional<Item> findById(Long id) {

        Map<String, Long> params = new HashMap<>();
        params.put("id", id);
        return Optional.ofNullable(this.webClientBuilder.build()
                .get()
                .uri("http://product-service/{id}", params)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Product.class)
                .map(product -> new Item(product, new Random().nextInt(10)+1))
                .block());
    }
}
