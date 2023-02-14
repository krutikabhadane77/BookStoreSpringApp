package com.example.bookstoreapplication.model;

import com.example.bookstoreapplication.dto.BookDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Table(name = "book")
@Data
@NoArgsConstructor
public class Book {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookId;
    private String bookName;
    private String authorName;
    private String bookDescription;
    private String bookImg;
    private long price;
    private long quantity;
    //Create Constructor
    public Book(BookDTO bookDTO){
        this.bookName=bookDTO.getBookName();
        this.authorName=bookDTO.getAuthorName();
        this.bookDescription=bookDTO.getBookDescription();
        this.bookImg=bookDTO.getBookImg();
        this.price=bookDTO.getPrice();
        this.quantity=bookDTO.getQuantity();
    }

}