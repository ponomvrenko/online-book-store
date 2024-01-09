package com.example.onlinebookstore.service;

import com.example.onlinebookstore.dto.order.OrderPlaceRequestDto;
import com.example.onlinebookstore.dto.order.OrderResponseDto;
import com.example.onlinebookstore.dto.order.OrderUpdateRequestDto;
import com.example.onlinebookstore.dto.order.item.OrderItemResponseDto;
import java.util.Set;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    Set<OrderItemResponseDto> findOrderItemsByOrderId(Long orderId, Pageable pageable);

    Set<OrderResponseDto> findOrderHistory(Long userId, Pageable pageable);

    OrderResponseDto placeOrder(Long userId, OrderPlaceRequestDto requestDto);

    OrderResponseDto updateOrderStatus(Long orderId, OrderUpdateRequestDto requestDto);

    OrderItemResponseDto findOrderItemByItemIdAndOrderId(Long itemId, Long orderId);
}
