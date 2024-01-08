package com.example.onlinebookstore.dto.order;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class OrderPlaceRequestDto {
    @NotBlank
    private String shippingAddress;
}
