package com.example.bookstoreapplication.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "cart")
@NoArgsConstructor
public class Cart {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartId;
    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "cart_user_id")
    private User userId;
    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "cart_book_id")
    private Book bookId;
    private long quantity;

    public Cart(User user,Book book,long quantity) {
        this.userId=user;
        this.bookId=book;
        this.quantity=quantity;
    }
}