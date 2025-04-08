package com.exe.spring_cloud.msvc.users_service.controllers;


import com.exe.spring_cloud.msvc.users_service.models.User;
import com.exe.spring_cloud.msvc.users_service.services.IUserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;

    private final Logger logger = LoggerFactory.getLogger(UserController.class);


    @GetMapping
    public List<User> findAll() {

        logger.info("Llamada a metodo del controller UserController:findAll");
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {

        logger.info("Llamada a metodo del controller UserController:findById");
        return userService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<?> findByUsername(@PathVariable String username) {

        logger.info("Llamada a metodo del controller UserController:getByUsername, login con: {},", username);
        return userService.findByUsername(username)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> create(@RequestBody User user) {

        logger.info("Llamada a metodo del controller UserController:create, creando: {},", user);
        return new ResponseEntity<>(userService.create(user), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody User user) {

        logger.info("Llamada a metodo del controller UserController:update, actualizando: {},", user);
        return userService.update(id, user)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> delete(@PathVariable Long id) {

        logger.info("Llamada a metodo del controller UserController:delete, eliminando: {},", id);
        userService.delete(id);
        return ResponseEntity.ok().build();
    }


}
