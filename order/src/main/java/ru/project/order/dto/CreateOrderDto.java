package ru.project.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateOrderDto {
    private int productId;
    private int orderCount;
    private Sign orderSign;
    public enum Sign {add, reduce};
}
