package com.example.onlinebookstore.repository;

import com.example.onlinebookstore.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    Optional<OrderItem> findByIdAndOrderId(Long itemId, Long orderId);
}
