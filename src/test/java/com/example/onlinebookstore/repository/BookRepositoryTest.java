package com.example.onlinebookstore.repository;

import com.example.onlinebookstore.model.Book;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    @DisplayName("""
            Find all books
            """)
    void findAll() {
        Pageable pageable = PageRequest.of(0, 5);
        Page<Book> books = bookRepository.findAll(pageable);

    }


    @Test
    void findById() {
        Book expectedBook = getDefaultBook();
        bookRepository.save(expectedBook);

        Book actualBook = bookRepository.findById(expectedBook.getId()).get();

        assertEquals(expectedBook.getId(), actualBook.getId());
        assertEquals(expectedBook.getTitle(), actualBook.getTitle());
    }


    @Test
        // 6:17
    void findAllByCategoriesId() {

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
}
