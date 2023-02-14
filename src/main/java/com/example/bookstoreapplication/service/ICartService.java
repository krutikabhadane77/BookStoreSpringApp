package com.example.bookstoreapplication.service;

import com.example.bookstoreapplication.dto.CartDTO;
import com.example.bookstoreapplication.model.Cart;

import java.util.List;
import java.util.Optional;

public interface ICartService {

    List<Cart> getAll();

    Optional<Cart> getById(Long id);
    Optional<Cart> getByUserId(Long userId);

    void deleteById(Long id);

    Cart updateById(CartDTO cartDTO, Long id);

    Cart UpdateQuantity(CartDTO cartDTO, Long id);

    Cart insert(CartDTO cartDTO);
}