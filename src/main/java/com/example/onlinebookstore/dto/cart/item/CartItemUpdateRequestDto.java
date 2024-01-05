package com.example.onlinebookstore.dto.cart.item;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CartItemUpdateRequestDto {
    @NotNull
    @Positive(message = "Quantity can't be lower than 0.")
    private int quantity;
}
