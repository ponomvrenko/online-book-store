package com.example.onlinebookstore.service.impl;

import com.example.onlinebookstore.dto.order.OrderPlaceRequestDto;
import com.example.onlinebookstore.dto.order.OrderResponseDto;
import com.example.onlinebookstore.dto.order.OrderUpdateRequestDto;
import com.example.onlinebookstore.dto.order.item.OrderItemResponseDto;
import com.example.onlinebookstore.exception.EntityNotFoundException;
import com.example.onlinebookstore.mapper.OrderItemMapper;
import com.example.onlinebookstore.mapper.OrderMapper;
import com.example.onlinebookstore.model.Book;
import com.example.onlinebookstore.model.CartItem;
import com.example.onlinebookstore.model.Order;
import com.example.onlinebookstore.model.OrderItem;
import com.example.onlinebookstore.model.ShoppingCart;
import com.example.onlinebookstore.model.User;
import com.example.onlinebookstore.repository.OrderItemRepository;
import com.example.onlinebookstore.repository.OrderRepository;
import com.example.onlinebookstore.repository.ShoppingCartRepository;
import com.example.onlinebookstore.repository.UserRepository;
import com.example.onlinebookstore.service.OrderService;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
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
    private final UserRepository userRepository;
    private final ShoppingCartRepository shoppingCartRepository;

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

    @Override
    @Transactional
    public OrderResponseDto placeOrder(Long userId, OrderPlaceRequestDto requestDto) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException(
                        "Can't find user by this ID: " + userId
                )
        );

        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(userId).orElseThrow(
                () -> new EntityNotFoundException(
                        "Can't find shopping cart for user with ID: " + userId
                )
        );

        Order order = setUpOrder(user, requestDto, shoppingCart);
        order.setOrderItems(setUpOrderItems(order, shoppingCart));
        order = orderRepository.save(order);
        return orderMapper.toDto(order);
    }

    private Set<OrderItem> setUpOrderItems(Order order, ShoppingCart shoppingCart) {
        Set<OrderItem> orderItems = new HashSet<>(shoppingCart.getCartItems().size());
        for (CartItem cartItem : shoppingCart.getCartItems()) {
            Book book = cartItem.getBook();
            OrderItem orderItem = new OrderItem();
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setBook(book);
            orderItem.setOrder(order);
            orderItem.setPrice(book.getPrice());
            orderItemRepository.save(orderItem);
        }
        return orderItems;
    }

    @Override
    @Transactional
    public OrderResponseDto updateOrderStatus(Long orderId, OrderUpdateRequestDto requestDto) {
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new EntityNotFoundException(
                        "Can't find order by current ID: " + orderId
                )
        );
        order.setStatus(Order.Status.valueOf(requestDto.getStatus().name()));
        orderRepository.save(order);
        return orderMapper.toDto(order);
    }

    private Order setUpOrder(
            User user,
            OrderPlaceRequestDto requestDto,
            ShoppingCart shoppingCart
    ) {
        Order order = new Order();
        order.setUser(user);
        order.setStatus(Order.Status.PENDING);
        order.setShippingAddress(requestDto.getShippingAddress());
        order.setTotal(countTotalPrice(shoppingCart));
        order.setOrderDate(LocalDateTime.now());
        return order;
    }

    private BigDecimal countTotalPrice(ShoppingCart shoppingCart) {
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (CartItem cartItem : shoppingCart.getCartItems()) {
            BigDecimal itemTotalPrice = cartItem.getBook().getPrice()
                    .multiply(BigDecimal.valueOf(cartItem.getQuantity()));
            totalPrice = totalPrice.add(itemTotalPrice);
        }
        return totalPrice;
    }
}
