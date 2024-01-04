package com.example.onlinebookstore.dto.order;

import com.example.onlinebookstore.dto.order.item.OrderItemResponseDto;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Data
public class OrderResponseDto {
    private Long id;
    private Long userId;
    private Set<OrderItemResponseDto> orderItems;
    private LocalDateTime orderDate;
    private BigDecimal total;
    private String status;
}
