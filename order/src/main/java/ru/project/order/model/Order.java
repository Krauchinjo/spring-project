package ru.project.order.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class Order {
    private Integer orderId;
    private Integer orderNumber;
    private String orderDate;
    private Integer customerId;
    private Integer productId;
    private String productName;
    private double price;
    private int count;
    private double sum;
}
