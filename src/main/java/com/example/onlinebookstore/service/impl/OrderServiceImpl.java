package com.example.onlinebookstore.service.impl;

import com.example.onlinebookstore.dto.order.OrderResponseDto;
import com.example.onlinebookstore.dto.order.item.OrderItemResponseDto;
import com.example.onlinebookstore.exception.EntityNotFoundException;
import com.example.onlinebookstore.mapper.OrderItemMapper;
import com.example.onlinebookstore.mapper.OrderMapper;
import com.example.onlinebookstore.model.OrderItem;
import com.example.onlinebookstore.repository.OrderItemRepository;
import com.example.onlinebookstore.repository.OrderRepository;
import com.example.onlinebookstore.service.OrderService;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;


    @Override
    public Set<OrderItemResponseDto> findOrderItemsByOrderId(Long orderId, Pageable pageable) {
        return orderItemRepository.findAll(pageable)
                .stream()
                .map(orderItemMapper::toDto)
                .collect(Collectors.toSet());
    }

    @Override
    @Transactional(readOnly = true)
    public Set<OrderResponseDto> findOrderHistory(Long userId, Pageable pageable) {
        return orderRepository.findAllByUserId(userId, pageable)
                .stream()
                .map(orderMapper::toDto)
                .collect(Collectors.toSet());
    }

    @Override
    public OrderItemResponseDto findOrderItemByItemIdAndOrderId(Long itemId, Long orderId) {
        OrderItem orderItem = orderItemRepository.findByIdAndOrderId(itemId, orderId).orElseThrow(
                () -> new EntityNotFoundException(
                        "Can't find order item by this order ID: "
                                + orderId + " and this item ID: " + itemId
                )
        );
        return orderItemMapper.toDto(orderItem);
    }
}
