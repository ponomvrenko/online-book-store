package com.example.onlinebookstore.dto.cart;

import com.example.onlinebookstore.dto.cart.item.CartItemResponseDto;
import java.util.Set;
import lombok.Data;

@Data
public class ShoppingCartResponseDto {
    private Long id;
    private Long userId;
    private Set<CartItemResponseDto> cartItems;
}
