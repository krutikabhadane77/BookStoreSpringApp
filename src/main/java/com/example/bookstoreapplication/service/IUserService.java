package com.example.bookstoreapplication.service;

import com.example.bookstoreapplication.dto.LoginDTO;
import com.example.bookstoreapplication.dto.UserDTO;
import com.example.bookstoreapplication.model.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    User register(UserDTO userDTO) throws Exception;

    List<User> getAll();

    Optional<User> getByUserId(long id);

    User getByEmail(String email);

    User updateUserByEmail(UserDTO userDTO, String email);

    String loginCheck(String email, String password);

    User forgotPassword(UserDTO userDTO, String email);

    String login(LoginDTO loginDTO);

    void deleteById(long id, String token);

    User getByToken(String token);
}