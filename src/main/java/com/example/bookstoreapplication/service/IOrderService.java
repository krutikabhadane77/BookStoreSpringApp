package com.example.bookstoreapplication.service;

import com.example.bookstoreapplication.dto.OrderDTO;
import com.example.bookstoreapplication.model.Order;

import java.util.List;

public interface IOrderService {
    Order insert(OrderDTO orderDTO) throws Exception;

    List<Order> getAll();

    Order getById(long id);

    Order updateById(long id, OrderDTO orderDTO) ;

    Order placeOrder(OrderDTO orderDTO);

    String cancelOrder(Long orderId, Long userId);

    //Deleting particular order details by id
    String deleteById(long orderId);
}