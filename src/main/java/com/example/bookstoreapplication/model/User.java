package com.example.bookstoreapplication.model;

import com.example.bookstoreapplication.dto.UserDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "user")
@Data
@NoArgsConstructor
public class User {
    //User table attributes
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private LocalDate dob;
    private String password;


    //Create Constructor
    public User(UserDTO userDTO){
        this.firstName=userDTO.getFirstName();
        this.lastName=userDTO.getLastName();
        this.email=userDTO.getEmail();
        this.address=userDTO.getAddress();
        this.dob=userDTO.getDob();
        this.password=userDTO.getPassword();
    }
}