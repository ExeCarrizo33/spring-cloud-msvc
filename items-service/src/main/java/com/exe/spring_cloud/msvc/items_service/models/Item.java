package com.exe.spring_cloud.msvc.items_service.models;

import lombok.Data;

@Data
public class Item {

    private Product product;
    private Integer quantity;

    private Double getTotal() {
        return product.getPrice() * quantity.doubleValue();
    }

}
