package com.exe.spring_cloud.msvc.items_service.controllers;

import com.exe.spring_cloud.msvc.items_service.models.Item;
import com.exe.spring_cloud.msvc.items_service.models.Product;
import com.exe.spring_cloud.msvc.items_service.services.ItemService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.CompletableFuture;

@RestController
@RequiredArgsConstructor
public class ItemController {

    private final Logger logger = LoggerFactory.getLogger(ItemController.class);
    private final ItemService itemService;
    private final CircuitBreakerFactory circuitBreakerFactory;

    @Value("${configuracion.texto}")
    private String text;


    @GetMapping
    public List<Item> findAll(@RequestParam(name = "name", required = false) String name,
                              @RequestHeader(name = "token-request", required = false) String token) {
        System.out.println("name = " + name);
        System.out.println("token = " + token);
        return itemService.findAll();
    }

    @GetMapping("/fetch-configs")
    public ResponseEntity<?> fetchConfigs(@Value("${server.port}") String port) {
        Map<String, String> json = new HashMap<>();
        json.put("text", text);
        json.put("port", port);
        logger.info(text);
        logger.info(port);
        return ResponseEntity.ok(json);
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> details(@PathVariable Long id) {
     Optional<Item> itemOptional =  circuitBreakerFactory.create("items").run(() -> itemService.findById(id), e -> {
            logger.error(e.getMessage());
            Product product = Product.builder()
                    .createdAt(LocalDate.now())
                    .id(1L)
                    .name("Camara Sony")
                    .price(500.00)
                    .build();
            return Optional.of(new Item(product, 5));
        });
        if (itemOptional.isPresent()) {
            return ResponseEntity.ok(itemOptional.get());
        }
        return ResponseEntity.status(404)
                .body(Collections.singletonMap(
                        "message",
                        "No existe el producto en el microservicio products"));
    }


    @CircuitBreaker(name = "items", fallbackMethod = "getFallBackMethodProduct")
    @TimeLimiter(name = "items" , fallbackMethod = "getFallBackMethodProduct")
    @GetMapping("details/{id}")
    public CompletableFuture<?> details3(@PathVariable Long id) {
        return  CompletableFuture.supplyAsync(() -> {
            Optional<Item> itemOptional =  itemService.findById(id);

            if (itemOptional.isPresent()) {
                return ResponseEntity.ok(itemOptional.get());
            }
            return ResponseEntity.status(404)
                    .body(Collections.singletonMap(
                            "message",
                            "No existe el producto en el microservicio products"));
        });

    }

    public ResponseEntity<?> getFallBackMethodProduct(Throwable e) {
        logger.error(e.getMessage());
        Product product = Product.builder()
                .createdAt(LocalDate.now())
                .id(1L)
                .name("Camara Sony")
                .price(500.00)
                .build();
        return ResponseEntity.ok(new Item(product, 5));
    }

}
