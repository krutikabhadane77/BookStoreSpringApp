package com.example.bookstoreapplication.controller;

import com.example.bookstoreapplication.dto.CartDTO;
import com.example.bookstoreapplication.dto.ResponseDTO;
import com.example.bookstoreapplication.model.Cart;
import com.example.bookstoreapplication.service.ICartService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    ICartService cartService;

    @PostMapping("/add")
    public ResponseEntity<ResponseDTO> insert(@Valid @RequestBody CartDTO cartDTO){
        Cart cart=cartService.insert(cartDTO);
        ResponseDTO responseDTO=new ResponseDTO("Cart details are added successfully!",cart);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @GetMapping("/getAll")
    public ResponseEntity<ResponseDTO> getAll(){
        List<Cart> cartList=cartService.getAll();
        ResponseDTO responseDTO=new ResponseDTO("Getting all cart details are successfully!",cartList);
        return new ResponseEntity<>(responseDTO,HttpStatus.FOUND);
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<ResponseDTO> getById(@PathVariable Long id){
        Optional<Cart> cart=cartService.getById(id);
        ResponseDTO responseDTO=new ResponseDTO("Getting cart details by id is successfully!",cart);
        return new ResponseEntity<>(responseDTO,HttpStatus.FOUND);
    }

    @GetMapping("/getByUserId/{userId}")
    public ResponseEntity<ResponseDTO> getByUserId(@PathVariable Long userId){
        Optional<Cart> cart=cartService.getByUserId(userId);
        ResponseDTO responseDTO=new ResponseDTO("Getting cart details by id is successfully!",cart);
        return new ResponseEntity<>(responseDTO,HttpStatus.FOUND);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseDTO> deleteById(@PathVariable Long id){
        cartService.deleteById(id);
        ResponseDTO responseDTO=new ResponseDTO("Cart details is deleted successfully!","Deleted cart id is: "+id);
        return new ResponseEntity<>(responseDTO,HttpStatus.GONE);
    }

    @PutMapping("/updateById/{id}")
    public ResponseEntity<ResponseDTO> updateById(@Valid @RequestBody CartDTO cartDTO,@PathVariable Long id){
        Cart cart=cartService.updateById(cartDTO,id);
        ResponseDTO responseDTO=new ResponseDTO("Cart details is updated successfully!",cart);
        return new ResponseEntity<>(responseDTO,HttpStatus.ACCEPTED);
    }

    @PutMapping("/updateQuantity/{id}")
    public ResponseEntity<ResponseDTO> updateQuantity(@Valid @RequestBody CartDTO cartDTO,@PathVariable Long id){
        Cart cart=cartService.UpdateQuantity(cartDTO,id);
        ResponseDTO responseDTO=new ResponseDTO("Cart quantity is updated successfully!",cart);
        return new ResponseEntity<>(responseDTO,HttpStatus.ACCEPTED);
    }

}