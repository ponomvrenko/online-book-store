package com.example.onlinebookstore.repository;

import com.example.onlinebookstore.model.ShoppingCart;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    @Query("SELECT sc FROM ShoppingCart sc JOIN FETCH sc.cartItems ci "
            + "LEFT JOIN ci.book WHERE sc.user.id = :userId")
    Optional<ShoppingCart> findByUserIdWithCartItems(Long userId);
}
