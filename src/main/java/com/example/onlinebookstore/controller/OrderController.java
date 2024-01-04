package com.example.onlinebookstore.controller;

import com.example.onlinebookstore.dto.order.OrderResponseDto;
import com.example.onlinebookstore.dto.order.item.OrderItemResponseDto;
import com.example.onlinebookstore.model.User;
import com.example.onlinebookstore.service.OrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@Tag(name = "Order management", description = "Endpoints for managing orders")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/orders")
public class OrderController {
    private final OrderService orderService;

    @GetMapping
    public Set<OrderResponseDto> getOrdersHistory(
            Authentication authentication,
            Pageable pageable
    ) {
        User user = (User) authentication.getPrincipal();
        return orderService.findOrderHistory(user.getId(), pageable);
    }

    @GetMapping("/{orderId}/items")
    public Set<OrderItemResponseDto> getOrderItemsByOrderId(
            @PathVariable Long orderId,
            Pageable pageable
    ) {
        return orderService.findOrderItemsByOrderId(orderId, pageable);
    }

    @GetMapping("/{orderId}/items/{itemId}")
    public OrderItemResponseDto getOrderItemByOrderIdAndItemId(
            @PathVariable Long orderId,
            @PathVariable Long itemId
    ) {
        return orderService.findOrderItemByItemIdAndOrderId(itemId, orderId);
    }

}
