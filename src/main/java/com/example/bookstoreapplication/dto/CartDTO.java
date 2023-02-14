package com.example.bookstoreapplication.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CartDTO {
    @NotNull(message = "User Id can not be null!")
    private Long userId;
    @NotNull(message = "Book Id can not be null!")
    private Long bookId;
    @NotNull(message = "Quantity can not be null!")
    private long quantity;
}