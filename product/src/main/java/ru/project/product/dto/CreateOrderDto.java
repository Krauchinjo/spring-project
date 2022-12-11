package ru.project.product.dto;

import lombok.*;
import org.apache.kafka.common.protocol.types.Field;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderDto {
    private int productId;
    private int orderCount;
    private Sign orderSign;
    public enum Sign {add,reduce};

}
