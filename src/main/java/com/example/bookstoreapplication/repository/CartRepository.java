package com.example.bookstoreapplication.repository;

import com.example.bookstoreapplication.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    @Query(value = "select * from book_store.cart where cart.cart_user_id=:userId",nativeQuery = true)
    Optional<Cart> findByUserId(Long userId);
    @Transactional
    @Modifying
    @Query(value = "delete from book_store.cart where cart.cart_id=:id",nativeQuery = true)
    void deleteById(Long id);
    @Query(value = "select cart_id from book_store.cart where cart.cart_user_id=:user",nativeQuery = true)
    Long findByCartUser(Long user);
}