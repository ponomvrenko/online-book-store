package com.example.onlinebookstore.dto.book;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import lombok.Data;
import org.hibernate.validator.constraints.ISBN;

@Data
public class CreateBookRequestDto {
    @Size(min = 1, max = 100)
    private String title;
    @NotNull
    @Size(min = 1, max = 40)
    private String author;
    @NotNull
    @ISBN
    private String isbn;
    @NotNull
    @Positive
    private BigDecimal price;
    private String description;
    private String coverImage;
}
