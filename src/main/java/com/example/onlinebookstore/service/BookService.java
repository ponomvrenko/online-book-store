package com.example.onlinebookstore.service;

import com.example.onlinebookstore.dto.book.BookDto;
import com.example.onlinebookstore.dto.book.CreateBookRequestDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookDto save(CreateBookRequestDto requestDto);

    BookDto findById(Long id);

    List<BookDto> findAll(Pageable pageable);

    void deleteById(Long id);

    BookDto updateById(Long id, CreateBookRequestDto requestDto);
}
