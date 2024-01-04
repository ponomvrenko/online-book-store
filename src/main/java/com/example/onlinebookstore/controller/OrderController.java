package com.example.onlinebookstore.controller;

import com.example.onlinebookstore.dto.order.OrderPlaceRequestDto;
import com.example.onlinebookstore.dto.order.OrderResponseDto;
import com.example.onlinebookstore.dto.order.OrderUpdateRequestDto;
import com.example.onlinebookstore.dto.order.item.OrderItemResponseDto;
import com.example.onlinebookstore.model.User;
import com.example.onlinebookstore.service.OrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public OrderResponseDto placeOrder(
            @RequestBody OrderPlaceRequestDto requestDto,
            Authentication authentication
    ) {
        User user = (User) authentication.getPrincipal();
        return orderService.placeOrder(user.getId(), requestDto);
    }

    @PatchMapping("/{id}")
    public OrderResponseDto updateOrderStatus(
            @PathVariable Long id,
            @RequestBody OrderUpdateRequestDto requestDto
    ) {
        return orderService.updateOrderStatus(id, requestDto);
    }
}
