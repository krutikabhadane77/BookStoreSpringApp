package com.example.bookstoreapplication.service;

import com.example.bookstoreapplication.dto.CartDTO;
import com.example.bookstoreapplication.exception.CartException;
import com.example.bookstoreapplication.model.Book;
import com.example.bookstoreapplication.model.Cart;
import com.example.bookstoreapplication.model.User;
import com.example.bookstoreapplication.repository.BookRepository;
import com.example.bookstoreapplication.repository.CartRepository;
import com.example.bookstoreapplication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService implements ICartService {

    @Autowired
    CartRepository cartRepo;

    @Autowired
    UserRepository userRepo;

    @Autowired
    BookRepository bookRepo;
    //Insert cart details
    @Override
    public Cart insert(CartDTO cartDTO){
        Optional<User> user=userRepo.findById(cartDTO.getUserId());
        Optional<Book> book=bookRepo.findById(cartDTO.getBookId());
        if(user.isPresent() && book.isPresent()){
            Cart cart=new Cart(user.get(),book.get(),cartDTO.getQuantity());
            cartRepo.save(cart);
            return cart;
        }else {
            throw new CartException("User id or Book id is not present! Please provide the correct details!");
        }
    }
    //Getting all cart details
    @Override
    public List<Cart> getAll(){
        List<Cart> cartList=cartRepo.findAll();
        return cartList;
    }
    //Getting particular cart details by id
    @Override
    public Optional<Cart> getById(Long id){
        Optional<Cart> cart=cartRepo.findById(id);
        if (cart.isPresent()){
            return cart;
        }else {
            throw new CartException("Sorry! We can not find the cart id: "+id);
        }
    }
    //Getting particular cart details by user id
    @Override
    public Optional<Cart> getByUserId(Long userId){
        Optional<Cart> cart=cartRepo.findByUserId(userId);
        if (cart.isPresent()){
            return cart;
        }else {
            throw new CartException("Sorry! We can not find the cart user id: "+userId);
        }
    }
    //Apply logic for Deleting particular cart details which will be found by id
    @Override
    public void deleteById(Long id){
        Optional<Cart> cart=cartRepo.findById(id);
        if (cart.isPresent()){
            cartRepo.deleteById(id);
        }else {
            throw new CartException("Sorry! We can not find cart id: "+id);
        }
    }
    //Updating particular cart details by id
    @Override
    public Cart updateById(CartDTO cartDTO,Long id){
        Optional<User> user=userRepo.findById(cartDTO.getUserId());
        Optional<Book> book=bookRepo.findById(cartDTO.getBookId());
        Cart cart=cartRepo.findById(id).get();
        if(cartRepo.findById(id).isPresent() && book.isPresent() && user.isPresent()){
            cart.setUserId(user.get());
            cart.setBookId(book.get());
            cart.setQuantity(cartDTO.getQuantity());
            cartRepo.save(cart);
            return cart;
        }else {
            throw new CartException("Sorry! Your details are incorrect! Please check!");
        }
    }
    //Updating quantity for particular cart by id
    @Override
    public Cart UpdateQuantity(CartDTO cartDTO, Long id){
        Cart cart=cartRepo.findById(id).get();
        if(cartRepo.findById(id).isPresent()){
            cart.setQuantity(cartDTO.getQuantity());
            cartRepo.save(cart);
            return cart;
        }else {
            throw new CartException("Sorry! We can not find cart id: "+id);
        }
    }
}