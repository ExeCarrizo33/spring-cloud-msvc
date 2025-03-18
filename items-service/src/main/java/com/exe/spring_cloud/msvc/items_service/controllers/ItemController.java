package com.exe.spring_cloud.msvc.items_service.controllers;

import com.exe.spring_cloud.msvc.items_service.models.Item;
import com.exe.spring_cloud.msvc.items_service.models.Product;
import com.exe.spring_cloud.msvc.items_service.services.ItemService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class ItemController {

    private final Logger logger = LoggerFactory.getLogger(ItemController.class);
    private final ItemService itemService;
    private final CircuitBreakerFactory circuitBreakerFactory;


    @GetMapping
    public List<Item> findAll(@RequestParam(name = "name", required = false) String name,
                              @RequestHeader(name = "token-request", required = false) String token) {
        System.out.println("name = " + name);
        System.out.println("token = " + token);
        return itemService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> details(@PathVariable Long id) {
     Optional<Item> itemOptional =  circuitBreakerFactory.create("items").run(() -> itemService.findById(id));// e -> {
//            logger.error(e.getMessage());
//            Product product = Product.builder()
//                    .createdAt(LocalDate.now())
//                    .id(1L)
//                    .name("Camara Sony")
//                    .price(500.00)
//                    .build();
//            return Optional.of(new Item(product, 5));
//        });
        if (itemOptional.isPresent()) {
            return ResponseEntity.ok(itemOptional.get());
        }
        return ResponseEntity.status(404)
                .body(Collections.singletonMap(
                        "message",
                        "No existe el producto en el microservicio products"));
    }


}
