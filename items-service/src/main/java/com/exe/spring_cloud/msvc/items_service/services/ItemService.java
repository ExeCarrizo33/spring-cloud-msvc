package com.exe.spring_cloud.msvc.items_service.services;

import com.exe.spring_cloud.msvc.items_service.models.Item;

import java.util.List;
import java.util.Optional;

public interface ItemService {

    List<Item> findAll();

    Optional<Item> findById(Long id);
}

