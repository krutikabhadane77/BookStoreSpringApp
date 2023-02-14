package com.example.bookstoreapplication.controller;

import com.example.bookstoreapplication.dto.OrderDTO;
import com.example.bookstoreapplication.dto.ResponseDTO;
import com.example.bookstoreapplication.model.Order;
import com.example.bookstoreapplication.service.IOrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    IOrderService orderService;

    @PostMapping("/add")
    public ResponseEntity<ResponseDTO> insert(@Valid @RequestBody OrderDTO orderDTO) throws Exception{
        Order order=orderService.insert(orderDTO);
        ResponseDTO responseDTO=new ResponseDTO("Order details are submitted successfully!",order);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }
    @PostMapping("/placeOrder")
    public ResponseEntity<ResponseDTO> placeOrder(@Valid @RequestBody OrderDTO orderDTO) {
        Order order=orderService.placeOrder(orderDTO);
        ResponseDTO responseDTO=new ResponseDTO("Order details are submitted successfully!",order);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }


    @GetMapping("/getAll")
    public ResponseEntity<ResponseDTO> getAll(){
        List<Order> orderList=orderService.getAll();
        ResponseDTO responseDTO=new ResponseDTO("Getting all order details are successfully!",orderList);
        return new ResponseEntity<>(responseDTO,HttpStatus.FOUND);
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<ResponseDTO> getById(@PathVariable Long id){
        Order order=orderService.getById(id);
        ResponseDTO responseDTO=new ResponseDTO("Getting order details by id is successfully!",order);
        return new ResponseEntity<>(responseDTO,HttpStatus.FOUND);
    }

    @DeleteMapping("/delete/{orderId}")
    public ResponseEntity<ResponseDTO> deleteById(@PathVariable Long orderId){
        String result=orderService.deleteById(orderId);
        ResponseDTO responseDTO=new ResponseDTO("Order details is deleted successfully!",result);
        return new ResponseEntity<>(responseDTO,HttpStatus.GONE);
    }
    @PutMapping("/updateById/{id}")
    public ResponseEntity<ResponseDTO> updateById(@Valid @PathVariable Long id,@RequestBody OrderDTO orderDTO){
        Order order=orderService.updateById(id,orderDTO);
        ResponseDTO responseDTO=new ResponseDTO("Searched details is updated successfully!",order);
        return new ResponseEntity<>(responseDTO,HttpStatus.ACCEPTED);
    }

    @PutMapping("/cancelOrder/{userId}/{orderId}")
    public ResponseEntity<ResponseDTO> cancelOrder(@PathVariable Long orderId,@PathVariable Long userId) {
        String order = orderService.cancelOrder(orderId,userId);
        ResponseDTO response= new ResponseDTO("Order Cancelled ",  order);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}