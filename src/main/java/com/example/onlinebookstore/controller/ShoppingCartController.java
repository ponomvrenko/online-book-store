package com.example.onlinebookstore.controller;

import com.example.onlinebookstore.dto.cart.ShoppingCartResponseDto;
import com.example.onlinebookstore.dto.cart.item.CartItemRequestDto;
import com.example.onlinebookstore.dto.cart.item.CartItemUpdateRequestDto;
import com.example.onlinebookstore.model.User;
import com.example.onlinebookstore.service.ShoppingCartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Shopping cart management", description = "Endpoints for managing shopping carts")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/cart")
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Add book to shopping cart",
            description = "Add book to shopping cart")
    public ShoppingCartResponseDto addToShoppingCart(
            @RequestBody CartItemRequestDto requestDto,
            Authentication authentication
    ) {
        User user = (User) authentication.getPrincipal();
        return shoppingCartService.addToShoppingCart(requestDto, user.getId());
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping
    @Operation(summary = "Get user's shopping cart",
            description = "Get user's shopping cart")
    public ShoppingCartResponseDto getShoppingCart(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return shoppingCartService.getShoppingCartByUserId(user.getId());
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping("/cart-items/{id}")
    @Operation(summary = "Update book's quantity",
            description = "Update book's quantity")
    public ShoppingCartResponseDto updateQuantityOfBook(
            @PathVariable Long id,
            @RequestBody @Valid CartItemUpdateRequestDto requestDto,
            Authentication authentication
    ) {
        User user = (User) authentication.getPrincipal();
        return shoppingCartService.updateCartItem(id, requestDto, user.getId());
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping("/cart-items/{id}")
    @Operation(summary = "Delete cart item",
            description = "Delete book from shopping cart by id")
    public void deleteCartItemById(Authentication authentication, @PathVariable Long id) {
        User user = (User) authentication.getPrincipal();
        shoppingCartService.deleteCartItemById(user.getId(), id);
    }
}
