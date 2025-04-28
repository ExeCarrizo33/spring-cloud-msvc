package com.exe.spring_cloud.msvc.items_service.controllers;

import com.exe.spring_cloud.msvc.items_service.models.Item;
import com.exe.spring_cloud.msvc.items_service.services.ItemService;
import com.exe.spring_cloud.msvc.libs_common_service.models.Product;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.CompletableFuture;

@RefreshScope
@RestController
public class ItemController {

    private final Logger logger = LoggerFactory.getLogger(ItemController.class);
    private final ItemService itemService;
    private final CircuitBreakerFactory circuitBreakerFactory;



    private final Environment env;

    public ItemController(@Qualifier("itemServiceWebClient") ItemService itemService, CircuitBreakerFactory circuitBreakerFactory, Environment env) {
        this.itemService = itemService;
        this.circuitBreakerFactory = circuitBreakerFactory;
        this.env = env;
    }


    @GetMapping
    public List<Item> findAll(@RequestParam(name = "name", required = false) String name,
                              @RequestHeader(name = "token-request", required = false) String token) {
        logger.info("Llamada a metodo del controller ItemController:findAll");
        logger.info("Request Parameter: {}" , name);
        logger.info("Token {}", token);
        System.out.println("name = " + name);
        System.out.println("token = " + token);
        return itemService.findAll();
    }

    @GetMapping("/fetch-configs")
    public ResponseEntity<?> fetchConfigs(@Value("${server.port}") String port) {
        Map<String, String> json = new HashMap<>();
        json.put("port", port);
        logger.info(port);
        if (env.getActiveProfiles().length > 0 && env.getActiveProfiles()[0].equals("dev")) {
            json.put("autor.nombre", env.getProperty("configuracion.autor.nombre"));
            json.put("autor.email", env.getProperty("configuracion.autor.email"));
        }
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

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Product product) {
        logger.info("Product creando: {}", product);
        return ResponseEntity.status(HttpStatus.CREATED).body(itemService.save(product));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> update(@RequestBody Product product, @PathVariable Long id) {
        logger.info("Product actualizando: {}", product);
        return ResponseEntity.status(HttpStatus.CREATED).body(itemService.update(product, id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        logger.info("Product eliminado: {}", id);
        this.itemService.delete(id);
    }




}
