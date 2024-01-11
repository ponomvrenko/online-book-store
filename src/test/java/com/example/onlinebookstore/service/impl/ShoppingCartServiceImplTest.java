package com.example.onlinebookstore.service.impl;

import com.example.onlinebookstore.dto.cart.ShoppingCartResponseDto;
import com.example.onlinebookstore.dto.cart.item.CartItemRequestDto;
import com.example.onlinebookstore.mapper.ShoppingCartMapper;
import com.example.onlinebookstore.model.*;
import com.example.onlinebookstore.repository.BookRepository;
import com.example.onlinebookstore.repository.CartItemRepository;
import com.example.onlinebookstore.repository.ShoppingCartRepository;
import com.example.onlinebookstore.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testcontainers.shaded.org.apache.commons.lang3.builder.EqualsBuilder;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ShoppingCartServiceImplTest {
    @InjectMocks
    private ShoppingCartServiceImpl shoppingCartService;
    @Mock
    private ShoppingCartRepository shoppingCartRepository;
    @Mock
    private ShoppingCartMapper shoppingCartMapper;

    @Test
    @DisplayName("Returns valid shopping cart with valid user ID")
    void getShoppingCartByUserId_WithValidUserId_ReturnResponseDto() {
        ShoppingCart shoppingCart = getDefaultShoppingCart();
        User user = getDefaultUser();
        ShoppingCartResponseDto expected = getDefaultResponseDto();
        when(shoppingCartRepository.findByUserId(anyLong())).thenReturn(Optional.of(shoppingCart));
        when(shoppingCartMapper.toDto(shoppingCart)).thenReturn(expected);

        ShoppingCartResponseDto actual = shoppingCartService.getShoppingCartByUserId(user.getId());

        EqualsBuilder.reflectionEquals(expected, actual, "id");
    }

    private ShoppingCartResponseDto getDefaultResponseDto() {
        return new ShoppingCartResponseDto()
                .setId(1L)
                .setUserId(1L)
                .setCartItems(Set.of());
    }

    private ShoppingCart getDefaultShoppingCart() {
        return new ShoppingCart()
                .setId(1L)
                .setCartItems(Set.of())
                .setUser(getDefaultUser());
    }

    private User getDefaultUser() {
        return new User()
                .setId(1L)
                .setEmail("defaultuser@gmail.com")
                .setPassword("12345678")
                .setFirstName("Peter")
                .setLastName("Parker")
                .setRoles(Set.of())
                .setShippingAddress("USA, New York, Empire State Building");
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