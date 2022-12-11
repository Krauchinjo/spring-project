package ru.project.product.dto;

import lombok.Data;

@Data
public class ProductRequest {
    String name;
    String description;
    double price;
    int count;
}
