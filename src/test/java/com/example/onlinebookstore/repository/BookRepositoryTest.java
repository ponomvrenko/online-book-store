package com.example.onlinebookstore.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.onlinebookstore.model.Book;
import com.example.onlinebookstore.model.Category;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.shaded.org.apache.commons.lang3.builder.EqualsBuilder;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    @DisplayName("Find books by category with existing category")
    @Sql(scripts = {
            "classpath:database/books/add-three-books.sql",
            "classpath:database/categories/add-categories.sql",
            "classpath:database/books-categories/add-books-categories.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:database/books-categories/delete-books-categories.sql",
            "classpath:database/categories/delete-categories.sql",
            "classpath:database/books/delete-books.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void findAllByCategoriesId_WithScienceCategory_ShouldReturnOneBook() {
        Book scienceBook = new Book()
                .setId(1L)
                .setTitle("Critical thinking")
                .setAuthor("Jonathan Haber")
                .setIsbn("978-617-8025-53-3")
                .setPrice(BigDecimal.valueOf(400))
                .setDescription("Be critical to everything");

        Category scienceCategory = new Category()
                .setId(1L)
                .setName("Science")
                .setDescription("Science genre");

        List<Book> expected = List.of(scienceBook);
        List<Book> actual = bookRepository.findAllByCategoriesId(scienceCategory.getId());

        assertEquals(1, actual.size());
        EqualsBuilder.reflectionEquals(expected.get(0), actual.get(0), "id");
    }
}
