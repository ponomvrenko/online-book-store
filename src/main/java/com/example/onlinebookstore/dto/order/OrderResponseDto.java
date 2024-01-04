package com.example.onlinebookstore.dto.order;

import com.example.onlinebookstore.dto.order.item.OrderItemResponseDto;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.Data;

@Data
public class OrderResponseDto {
    private Long id;
    private Long userId;
    private Set<OrderItemResponseDto> orderItems;
    private LocalDateTime orderDate;
    private BigDecimal total;
    private String status;
}
