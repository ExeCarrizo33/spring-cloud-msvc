package com.exe.spring_cloud.msvc.items_service.models;

import lombok.Data;

@Data
public class Item {

    private Product product;
    private Integer quantity;

    public Item(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Double getTotal() {
        return product.getPrice() * quantity;
    }

}
