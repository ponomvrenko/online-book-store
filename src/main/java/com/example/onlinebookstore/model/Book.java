package com.example.onlinebookstore.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import lombok.Data;
import lombok.NonNull;

@Entity
@Table(name = "books")
@Data
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private String title;
    @NonNull
    private String author;
    @NonNull
    @Column(unique = true)
    private String isbn;
    @NonNull
    private BigDecimal price;
    private String description;
    private String coverImage;

    public Book() {

    }
}
