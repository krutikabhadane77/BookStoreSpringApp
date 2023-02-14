package com.example.bookstoreapplication.service;

import com.example.bookstoreapplication.dto.BookDTO;
import com.example.bookstoreapplication.model.Book;

import java.util.List;
import java.util.Optional;

public interface IBookService {
    Book insert(BookDTO bookDTO);

    List<Book> getAllBook();

    Optional<Book> getById(Long id);

    void deleteById(Long id);


    Book updateBookById(BookDTO bookDTO, Long id);


    Book updateQuantity(BookDTO bookDTO, Long id);
}