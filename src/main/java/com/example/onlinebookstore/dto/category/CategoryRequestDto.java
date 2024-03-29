package com.example.onlinebookstore.dto.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CategoryRequestDto {
    @NotBlank
    @Size(min = 1, max = 30)
    private String name;
    @NotBlank
    @Size(min = 1, max = 100)
    private String description;
}
