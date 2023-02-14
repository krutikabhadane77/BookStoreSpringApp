package com.example.bookstoreapplication.controller;

import com.example.bookstoreapplication.dto.BookDTO;
import com.example.bookstoreapplication.dto.ResponseDTO;
import com.example.bookstoreapplication.model.Book;
import com.example.bookstoreapplication.service.IBookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    IBookService bookService;

    @PostMapping("/insert")
    public ResponseEntity<ResponseDTO> insert(@Valid @RequestBody BookDTO bookDTO){
        Book book=bookService.insert(bookDTO);
        ResponseDTO responseDTO=new ResponseDTO("Book details has been submitted successfully!",book);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @GetMapping("/getAll")
    public ResponseEntity<ResponseDTO> getAllBook(){
        List<Book> bookList=bookService.getAllBook();
        ResponseDTO responseDTO=new ResponseDTO("Get all book details are successfully!",bookList);
        return new ResponseEntity<>(responseDTO,HttpStatus.FOUND);
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<ResponseDTO> getById(@PathVariable Long id){
        Optional<Book> book=bookService.getById(id);
        ResponseDTO responseDTO=new ResponseDTO("Searched book by id is successfully!",book);
        return new ResponseEntity<>(responseDTO,HttpStatus.FOUND);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseDTO> deleteById(@PathVariable Long id){
        bookService.deleteById(id);
        ResponseDTO responseDTO=new ResponseDTO("Book is deleted successfully!","Deleted book id is: "+id);
        return new ResponseEntity<>(responseDTO,HttpStatus.GONE);
    }

    @PutMapping("/updateById/{id}")
    public ResponseEntity<ResponseDTO> updateBookById(@Valid @RequestBody BookDTO bookDTO,@PathVariable Long id){
        Book book=bookService.updateBookById(bookDTO,id);
        ResponseDTO responseDTO=new ResponseDTO("Book details is updated successfully!",book);
        return new ResponseEntity<>(responseDTO,HttpStatus.ACCEPTED);
    }

    @PutMapping("/updateQuantity/{id}")
    public ResponseEntity<ResponseDTO> updateQuantity(@Valid @RequestBody BookDTO bookDTO,@PathVariable Long id){
        Book book=bookService.updateQuantity(bookDTO,id);
        ResponseDTO responseDTO=new ResponseDTO("Book details is updated successfully!",book);
        return new ResponseEntity<>(responseDTO,HttpStatus.ACCEPTED);
    }
}