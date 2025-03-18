package com.exe.spring_cloud.msvc.items_service.models;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class Product {

    private Long id;
    private String name;
    private Double price;
    private LocalDate createdAt;
}
