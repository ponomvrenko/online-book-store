package com.example.onlinebookstore.controller;

import com.example.onlinebookstore.dto.book.BookDtoWithoutCategoryIds;
import com.example.onlinebookstore.dto.book.BookResponseDto;
import com.example.onlinebookstore.dto.book.BookSearchParametersDto;
import com.example.onlinebookstore.dto.book.CreateBookRequestDto;
import com.example.onlinebookstore.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Book management", description = "Endpoints for managing books")
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/books")
public class BookController {

    private final BookService bookService;

    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping
    @Operation(summary = "Get all books",
            description = "Get a list of all existing books")
    public List<BookResponseDto> getAll(Pageable pageable) {
        return bookService.findAll(pageable);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping("/{id}")
    @Operation(summary = "Get book by ID",
            description = "Return a book by identifier")
    public BookResponseDto getBookById(@PathVariable Long id) {
        return bookService.findById(id);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping("/search")
    @Operation(summary = "Search book with given parameters",
            description = "Returns a book that matches given parameters")
    public List<BookDtoWithoutCategoryIds> search(BookSearchParametersDto searchParameters) {
        return bookService.search(searchParameters);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create new book",
            description = "Create a new book")
    public BookResponseDto createBook(@RequestBody @Valid CreateBookRequestDto requestDto) {
        return bookService.save(requestDto);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    @Operation(summary = "Update book by ID",
            description = "Updates book parameters with a new one")
    public BookResponseDto updateBookById(@PathVariable Long id,
                                          @RequestBody @Valid CreateBookRequestDto requestDto) {
        return bookService.updateById(id, requestDto);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete book by ID",
            description = "Removes the book")
    public void deleteBookById(@PathVariable Long id) {
        bookService.deleteById(id);
    }
}
