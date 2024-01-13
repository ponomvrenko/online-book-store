package com.example.onlinebookstore.repository;

import com.example.onlinebookstore.exception.EntityNotFoundException;
import com.example.onlinebookstore.model.ShoppingCart;
import com.example.onlinebookstore.model.User;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.shaded.org.apache.commons.lang3.builder.EqualsBuilder;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ShoppingCartRepositoryTest {
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Test
    @DisplayName("Find shopping cart by valid user ID and return DTO")
    @Sql(scripts = {
            "classpath:database/shopping-cart/add-default-shopping-cart.sql",
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:database/shopping-cart/delete-default-shopping-cart.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void findByUserId_WithValidId_ShouldReturnShoppingCart() {
        User user = getDefaultUser();
        ShoppingCart expected = getDefaultShoppingCart();

        ShoppingCart actual = shoppingCartRepository.findByUserId(user.getId()).orElseThrow(
                () -> new EntityNotFoundException(
                        "Can't find shopping cart by user ID" + user.getId()
                )
        );

        EqualsBuilder.reflectionEquals(expected, actual, "id");
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

    private ShoppingCart getDefaultShoppingCart() {
        return new ShoppingCart()
                .setId(1L)
                .setCartItems(Set.of())
                .setUser(getDefaultUser());
    }
}
