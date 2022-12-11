package ru.project.order.dto;

import lombok.Data;

@Data
public class OrderRequest {
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
