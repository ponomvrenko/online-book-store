package com.example.onlinebookstore.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

import com.example.onlinebookstore.dto.book.BookResponseDto;
import com.example.onlinebookstore.dto.book.CreateBookRequestDto;
import java.math.BigDecimal;
import java.util.Set;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import org.testcontainers.shaded.org.apache.commons.lang3.builder.EqualsBuilder;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookControllerTest {
    protected static MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    static void beforeAll(
            @Autowired WebApplicationContext applicationContext
    ) {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .apply(springSecurity())
                .build();
    }

    @Test
    @DisplayName("Create a new book")
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Sql(
            scripts = "classpath:database/books/delete-default-book.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
    )
    void createBook_ValidRequestDto_Success() throws Exception {
        CreateBookRequestDto createBookRequestDto = getDefaultCreateBookRequestDto();
        BookResponseDto expected = getDefaultBookResponseDto();

        String jsonRequest = objectMapper.writeValueAsString(createBookRequestDto);

        MvcResult result = mockMvc.perform(post("/books")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        BookResponseDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                BookResponseDto.class
        );
        assertNotNull(actual);
        assertNotNull(actual.getId());
        EqualsBuilder.reflectionEquals(expected, actual, "id");
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

    private BookResponseDto getDefaultBookResponseDto() {
        return new BookResponseDto()
                .setId(1L)
                .setTitle("Clean Code")
                .setAuthor("Robert Martin")
                .setDescription("Creation, analyze and refactor")
                .setPrice(BigDecimal.valueOf(1500))
                .setIsbn("978-5-4461-0960-9");
    }
}