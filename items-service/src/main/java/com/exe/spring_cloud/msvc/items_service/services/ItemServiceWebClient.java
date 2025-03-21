package com.exe.spring_cloud.msvc.items_service.services;

import com.exe.spring_cloud.msvc.items_service.models.Item;
import com.exe.spring_cloud.msvc.libs_common_service.models.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;

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
                .map(product -> new Item(product, new Random().nextInt(10) + 1))
                .collectList()
                .block();
    }

    @Override
    public Optional<Item> findById(Long id) {

        return Optional.ofNullable(this.webClientBuilder.build()
                .get()
                .uri("http://product-service/{id}", Collections.singletonMap("id", id))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Product.class)
                .map(product -> new Item(product, new Random().nextInt(10) + 1))
                .block());
    }

    @Override
    public Product save(Product product) {

        return webClientBuilder.build().post()
                .uri("http://product-service")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(product)
                .retrieve()
                .bodyToMono(Product.class)
                .block();
    }

    @Override
    public Product update(Product product, Long id) {

        return webClientBuilder.build().put()
                .uri("http://product-service/{id}", Collections.singletonMap("id", id))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(product)
                .retrieve()
                .bodyToMono(Product.class)
                .block();
    }

    @Override
    public void delete(Long id) {
        webClientBuilder.build().delete()
                .uri("http://product-service/{id}", Collections.singletonMap("id", id))
                .retrieve()
                .bodyToMono(Void.class)
                .block();

    }
}
