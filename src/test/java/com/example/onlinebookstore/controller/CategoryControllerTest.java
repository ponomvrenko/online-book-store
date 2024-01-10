package com.example.onlinebookstore.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import com.example.onlinebookstore.dto.category.CategoryResponseDto;
import lombok.SneakyThrows;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CategoryControllerTest {
    protected static MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    private static CategoryResponseDto publicist;
    private static CategoryResponseDto drama;
    private static CategoryResponseDto science;

    @BeforeAll
    static void beforeAll(
            @Autowired DataSource dataSource,
            @Autowired WebApplicationContext applicationContext
    ) throws SQLException {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .apply(springSecurity())
                .build();
        teardown(dataSource);
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(true);
            ScriptUtils.executeSqlScript(
                    connection,
                    new ClassPathResource("database/books/add-three-books.sql")
            );
            ScriptUtils.executeSqlScript(
                    connection,
                    new ClassPathResource("database/books/add-categories.sql")
            );
            ScriptUtils.executeSqlScript(
                    connection,
                    new ClassPathResource("database/books/add-books-categories.sql")
            );
        }

        CategoryResponseDto publicist = new CategoryResponseDto()
                .setId(1L)
                .setName("Publicist")
                .setDescription("Journalistic genre");

        CategoryResponseDto drama = new CategoryResponseDto()
                .setId(2L)
                .setName("Drama")
                .setDescription("Drama genre");

        CategoryResponseDto science = new CategoryResponseDto()
                .setId(3L)
                .setName("Science")
                .setDescription("Science genre");
    }

    @AfterAll
    static void afterAll(
            @Autowired DataSource dataSource
    ) {
        teardown(dataSource);
    }

    @SneakyThrows
    static void teardown(DataSource dataSource) {
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(true);
            ScriptUtils.executeSqlScript(
                    connection,
                    new ClassPathResource("database/books/delete-books.sql")
            );
            ScriptUtils.executeSqlScript(
                    connection,
                    new ClassPathResource("database/categories/delete-categories.sql")
            );
            ScriptUtils.executeSqlScript(
                    connection,
                    new ClassPathResource("database/categories/delete-books-categories.sql")
            );
        }
    }

    @WithMockUser(username = "user", roles = {"USER"})
    @Test
    @DisplayName("Get all categories")
    void getAll_ValidRequest_Success() throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                        get("/api/categories")
                )
                .andExpect(status().isOk())
                .andReturn();

        List<CategoryResponseDto> expected = List.of(publicist, drama, science);
        CategoryResponseDto[] actual = objectMapper.readValue(
                mvcResult.getResponse().getContentAsByteArray(),
                CategoryResponseDto[].class
        );

        assertEquals(3, actual.length);
        assertEquals(expected, Arrays.stream(actual).toList());
    }
}