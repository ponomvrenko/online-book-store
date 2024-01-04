package com.example.onlinebookstore.service.impl;

import com.example.onlinebookstore.dto.cart.ShoppingCartResponseDto;
import com.example.onlinebookstore.dto.cart.item.CartItemRequestDto;
import com.example.onlinebookstore.dto.cart.item.CartItemUpdateRequestDto;
import com.example.onlinebookstore.exception.EntityNotFoundException;
import com.example.onlinebookstore.mapper.ShoppingCartMapper;
import com.example.onlinebookstore.model.Book;
import com.example.onlinebookstore.model.CartItem;
import com.example.onlinebookstore.model.ShoppingCart;
import com.example.onlinebookstore.model.User;
import com.example.onlinebookstore.repository.BookRepository;
import com.example.onlinebookstore.repository.CartItemRepository;
import com.example.onlinebookstore.repository.ShoppingCartRepository;
import com.example.onlinebookstore.repository.UserRepository;
import com.example.onlinebookstore.service.ShoppingCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final CartItemRepository cartItemRepository;
    private final ShoppingCartMapper shoppingCartMapper;

    @Override
    @Transactional
    public ShoppingCartResponseDto addToShoppingCart(
            CartItemRequestDto requestDto,
            Long userId
    ) {
        CartItem cartItem = new CartItem();
        cartItem.setQuantity(requestDto.getQuantity());

        Long bookId = requestDto.getBookId();
        Book book = bookRepository.findById(bookId).orElseThrow(
                () -> new EntityNotFoundException(
                        "Can't find book by this ID: " + bookId
                )
        );
        cartItem.setBook(book);

        User user = userRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException(
                        "Can't find user by this ID: " + userId
                )
        );
        ShoppingCart shoppingCartFromDB =
                shoppingCartRepository.findByUserId(userId)
                        .orElseGet(() -> {
                            ShoppingCart shoppingCart = new ShoppingCart();
                            shoppingCart.setUser(user);
                            shoppingCartRepository.save(shoppingCart);
                            return shoppingCart;
                        });
        cartItem.setShoppingCart(shoppingCartFromDB);
        cartItemRepository.save(cartItem);
        shoppingCartFromDB.getCartItems().add(cartItem);

        return shoppingCartMapper.toDto(shoppingCartFromDB);
    }

    @Override
    public ShoppingCartResponseDto getShoppingCartByUserId(Long userId) {
        return shoppingCartMapper.toDto(findShoppingCartByUserId(userId));
    }

    @Override
    @Transactional
    public ShoppingCartResponseDto updateCartItem(
            Long cartItemId,
            CartItemUpdateRequestDto requestDto,
            Long userId
    ) {
        ShoppingCart shoppingCart = findShoppingCartByUserId(userId);
        CartItem cartItem =
                findCartItemByShoppingCartIdAndCartItemId(cartItemId, shoppingCart);
        cartItem.setQuantity(requestDto.getQuantity());
        cartItemRepository.save(cartItem);
        return shoppingCartMapper.toDto(shoppingCart);
    }

    @Override
    public void deleteCartItemById(Long userId, Long cartItemId) {
        ShoppingCart shoppingCart = findShoppingCartByUserId(userId);
        CartItem cartItem =
                findCartItemByShoppingCartIdAndCartItemId(cartItemId, shoppingCart);
        cartItemRepository.delete(cartItem);
    }

    private CartItem findCartItemByShoppingCartIdAndCartItemId(Long cartItemId, ShoppingCart shoppingCart) {
        return cartItemRepository.findByIdAndShoppingCartId(cartItemId, shoppingCart.getId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Can't find cart item by this "
                                + "item ID: " + cartItemId
                ));
    }

    private ShoppingCart findShoppingCartByUserId(Long userId) {
        return shoppingCartRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Can't find shopping cart by this "
                                + "user ID: " + userId
                ));
    }
}
