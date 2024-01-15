package com.example.onlinebookstore.service.impl;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import com.example.onlinebookstore.dto.cart.ShoppingCartResponseDto;
import com.example.onlinebookstore.mapper.ShoppingCartMapper;
import com.example.onlinebookstore.model.ShoppingCart;
import com.example.onlinebookstore.model.User;
import com.example.onlinebookstore.repository.ShoppingCartRepository;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testcontainers.shaded.org.apache.commons.lang3.builder.EqualsBuilder;

@ExtendWith(MockitoExtension.class)
class ShoppingCartServiceImplTest {
    @Mock
    private ShoppingCartRepository shoppingCartRepository;
    @Mock
    private ShoppingCartMapper shoppingCartMapper;
    @InjectMocks
    private ShoppingCartServiceImpl shoppingCartService;

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
}
