package com.example.onlinebookstore.service;

import com.example.onlinebookstore.dto.book.BookDtoWithoutCategoryIds;
import com.example.onlinebookstore.dto.book.BookResponseDto;
import com.example.onlinebookstore.dto.book.BookSearchParametersDto;
import com.example.onlinebookstore.dto.book.CreateBookRequestDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookResponseDto save(CreateBookRequestDto requestDto);

    BookResponseDto findById(Long id);

    List<BookResponseDto> findAll(Pageable pageable);

    void deleteById(Long id);

    BookResponseDto updateById(Long id, CreateBookRequestDto requestDto);

    List<BookDtoWithoutCategoryIds> findAllByCategoryId(Long categoryId);

    List<BookDtoWithoutCategoryIds> search(BookSearchParametersDto searchParameters);
}
