package com.example.bookstoreapplication.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
public class OrderDTO {
    @PastOrPresent(message = "Order date should be past or present!")
    private LocalDate date;
    @NotNull(message = "Price can not be null!")
    private long price;
    @NotNull(message = "Quantity can not be null!")
    private long quantity;
    @NotNull(message = "Address can not be null!")
    private String address;
    @NotNull(message = "User Id can not be null!")
    private Long user;
    @NotNull(message = "Book Id can not be null!")
    private List<Long> book;
    private boolean cancel;

    public boolean getCancel() {
        return cancel;
    }
}