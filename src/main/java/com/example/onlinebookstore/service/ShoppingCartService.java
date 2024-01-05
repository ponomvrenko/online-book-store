package com.example.onlinebookstore.service;

import com.example.onlinebookstore.dto.cart.ShoppingCartResponseDto;
import com.example.onlinebookstore.dto.cart.item.CartItemRequestDto;
import com.example.onlinebookstore.dto.cart.item.CartItemUpdateRequestDto;

public interface ShoppingCartService {
    ShoppingCartResponseDto addToShoppingCart(CartItemRequestDto requestDto, Long userId);

    ShoppingCartResponseDto getShoppingCartByUserId(Long userId);

    ShoppingCartResponseDto updateCartItem(
            Long cartItemId,
            CartItemUpdateRequestDto requestDto,
            Long userId
    );

    void deleteCartItemById(Long userId, Long cartItemId);
}
