package com.example.onlinebookstore.dto.order;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class OrderUpdateRequestDto {
    @NotBlank
    private String status;
}
