package com.example.onlinebookstore.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.example.onlinebookstore.dto.book.BookResponseDto;
import com.example.onlinebookstore.dto.book.CreateBookRequestDto;
import com.example.onlinebookstore.exception.EntityNotFoundException;
import com.example.onlinebookstore.mapper.BookMapper;
import com.example.onlinebookstore.model.Book;
import com.example.onlinebookstore.repository.BookRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.testcontainers.shaded.org.apache.commons.lang3.builder.EqualsBuilder;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    @InjectMocks
    private BookServiceImpl bookService;
    @Mock
    private BookRepository bookRepository;
    @Mock
    private BookMapper bookMapper;

    @Test
    @DisplayName("""
            save() works properly and returns dto
            """)
    void save_WithValidRequestBookDto_ShouldReturnCorrectDto() {
        Book book = getDefaultBook();
        BookResponseDto expected = getDefaultBookResponseDto();
        CreateBookRequestDto createBookRequestDto = getDefaultCreateBookRequestDto();
        when(bookMapper.toEntity(createBookRequestDto)).thenReturn(book);
        when(bookRepository.save(book)).thenReturn(book);
        when(bookMapper.toDto(book)).thenReturn(expected);

        BookResponseDto actual = bookService.save(createBookRequestDto);

        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getTitle(), actual.getTitle());
        assertEquals(expected.getAuthor(), actual.getAuthor());
        assertEquals(expected.getDescription(), actual.getDescription());
        assertEquals(expected.getPrice(), actual.getPrice());
        assertEquals(expected.getIsbn(), actual.getIsbn());
    }

    @Test
    @DisplayName("""
            Verify the book title with valid ID was returned
            """)
    void findById_WithValidBookId_ShouldReturnValidBook() {
        Book book = getDefaultBook();
        String expected = book.getTitle();
        BookResponseDto bookResponseDto = getDefaultBookResponseDto();
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));
        when(bookMapper.toDto(book)).thenReturn(bookResponseDto);

        String actual = bookService.findById(book.getId()).getTitle();

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("""
            findById() should return Exception with invalid ID
            """)
    void findById_WithNotValidBookId_ShouldThrowException() {
        Long invalidBookId = 100L;
        when(bookRepository.findById(invalidBookId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(
                EntityNotFoundException.class,
                () -> bookService.findById(invalidBookId)
        );

        String expected = "The book with ID: " + invalidBookId
                + ", unfortunately was not be found.";
        String actual = exception.getMessage();
        assertEquals(expected, actual);
        verify(bookRepository, times(1)).findById(invalidBookId);
        verifyNoMoreInteractions(bookRepository);
    }

    @Test
    @DisplayName("""
            Getting all books from database with valid books
            """)
    void findAll_WithValidBooks_ShouldReturnList() {
        Book book = getDefaultBook();
        List<Book> books = List.of(book);
        Pageable pageable = PageRequest.of(0, 5);
        Page<Book> page = new PageImpl<>(books, pageable, books.size());
        BookResponseDto bookResponseDto = getDefaultBookResponseDto();
        List<BookResponseDto> expected = List.of(bookResponseDto);
        when(bookRepository.findAll(pageable)).thenReturn(page);
        when(bookMapper.toDto(book)).thenReturn(bookResponseDto);

        List<BookResponseDto> actual = bookService.findAll(pageable);

        assertEquals(expected.size(), actual.size());
        assertEquals(expected.get(0).getId(), actual.get(0).getId());
        assertEquals(expected.get(0).getTitle(), actual.get(0).getTitle());
        assertEquals(expected.get(0).getAuthor(), actual.get(0).getAuthor());
        assertEquals(expected.get(0).getPrice(), actual.get(0).getPrice());
        assertEquals(expected.get(0).getIsbn(), actual.get(0).getIsbn());
        assertEquals(expected.get(0).getDescription(), actual.get(0).getDescription());
    }

    @Test
    @DisplayName("""
            Updated book price and return response dto
            """)
    void updateById_WithValidId_ShouldReturnBookResponseDto() {
        Book book = getDefaultBook();
        CreateBookRequestDto createBookRequestDto = getDefaultCreateBookRequestDto();
        createBookRequestDto.setPrice(BigDecimal.valueOf(2000));
        BookResponseDto expected = getDefaultBookResponseDto();
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));
        when(bookMapper.toEntity(createBookRequestDto)).thenReturn(book);
        when(bookMapper.toDto(book)).thenReturn(expected);
        when(bookRepository.save(book)).thenReturn(book);

        BookResponseDto actual = bookService.updateById(book.getId(), createBookRequestDto);

        EqualsBuilder.reflectionEquals(actual, expected);
        assertEquals(expected.getPrice(), actual.getPrice());
    }

    @Test
    @DisplayName("""
            updateById() should throw Exception with incorrect ID
            """)
    void updateById_WithNotValidId_ShouldThrowException() {
        Long invalidId = 100L;
        CreateBookRequestDto createBookRequestDto = getDefaultCreateBookRequestDto();
        when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(
                EntityNotFoundException.class,
                () -> bookService.updateById(invalidId, createBookRequestDto)
        );

        String expected = "The book with ID: " + invalidId + ", unfortunately was not be founded.";
        String actual = exception.getMessage();
        assertEquals(expected, actual);
    }

    private Book getDefaultBook() {
        return new Book()
                .setId(1L)
                .setTitle("Clean Code")
                .setAuthor("Robert Martin")
                .setDescription("Creation, analyze and refactor")
                .setPrice(BigDecimal.valueOf(1500))
                .setIsbn("978-5-4461-0960-9");
    }

    private BookResponseDto getDefaultBookResponseDto() {
        return new BookResponseDto()
                .setId(1L)
                .setTitle("Clean Code")
                .setAuthor("Robert Martin")
                .setDescription("Creation, analyze and refactor")
                .setPrice(BigDecimal.valueOf(1500))
                .setIsbn("978-5-4461-0960-9");
    }

    private CreateBookRequestDto getDefaultCreateBookRequestDto() {
        return new CreateBookRequestDto()
                .setTitle("Clean Code")
                .setAuthor("Robert Martin")
                .setIsbn("978-5-4461-0960-9")
                .setPrice(BigDecimal.valueOf(1500))
                .setDescription("Creation, analyze and refactor")
                .setCategoryIds(Set.of(1L));
    }
}
