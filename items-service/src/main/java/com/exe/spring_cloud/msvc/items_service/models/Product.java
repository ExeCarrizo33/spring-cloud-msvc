package com.exe.spring_cloud.msvc.items_service.models;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Product {

    private Long id;
    private String name;
    private Double price;
    private LocalDate createAt;
}
