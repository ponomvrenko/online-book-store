package com.example.onlinebookstore.dto.cart.item;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CartItemRequestDto {
    @NotNull
    @Positive(message = "Book ID can't be lower than 0.")
    private Long bookId;
    @NotNull
    @Positive(message = "Quantity can't be lower than 0.")
    private int quantity;
}
