package com.example.bookstoreapplication.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "book_order")
public class Order {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;
    LocalDate localDate =LocalDate.now();
    private long price;
    private long quantity;
    private String address;
    @OneToOne//(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "order_user_id")
    private User user;
    @ManyToMany//(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "order_book_id")
    private List<Book> book;
    private boolean cancel;

    public Order(User user, List<Book> book, long price, long quantity, String address,boolean cancel) {
        this.price=price;
        this.quantity=quantity;
        this.address=address;
        this.user=user;
        this.book=book;
        this.cancel=cancel;
    }
}