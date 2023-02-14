package com.example.bookstoreapplication.service;

import com.example.bookstoreapplication.dto.OrderDTO;
import com.example.bookstoreapplication.exception.OrderException;
import com.example.bookstoreapplication.model.Book;
import com.example.bookstoreapplication.model.Order;
import com.example.bookstoreapplication.model.User;
import com.example.bookstoreapplication.repository.BookRepository;
import com.example.bookstoreapplication.repository.CartRepository;
import com.example.bookstoreapplication.repository.OrderRepository;
import com.example.bookstoreapplication.repository.UserRepository;
import com.example.bookstoreapplication.util.EmailSenderService;
import com.example.bookstoreapplication.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService implements IOrderService{
    @Autowired
    OrderRepository orderRepo;
    @Autowired
    UserRepository userRepo;
    @Autowired
    BookRepository bookRepo;
    @Autowired
    CartRepository cartRepo;
    @Autowired
    TokenUtil tokenUtil;
    @Autowired
    EmailSenderService emailSenderService;

    @Override
    public Order insert(OrderDTO orderDTO) throws Exception{
        Optional<User> user = userRepo.findById(orderDTO.getUser());
        List<Book> bookList=orderDTO.getBook().stream().map(book -> bookRepo.findById(book).get()).collect(Collectors.toList());
        long price=bookList.stream().mapToLong(n-> (n.getPrice())).sum();
        long quantity=bookList.stream().mapToLong(n-> n.getQuantity()).sum();
        if (user.isPresent()) {
            Order order = new Order(user.get(), bookList, price, quantity, orderDTO.getAddress(),orderDTO.getCancel());
            orderRepo.save(order);
            String token=tokenUtil.createToken(order.getOrderId());
            emailSenderService.sendEmail(user.get().getEmail(), "Order placed!","Hii...."+user.get().getFirstName()+" ! \n\n Your order has been placed successfully! Order details are below: \n\n Order id:  "+order.getOrderId()+"\n\n Order date:  "+order.getLocalDate()+"\n\n Order Price:  "+price+"\n Order quantity:  "+quantity+"\n Order address:  "+order.getAddress()+"\n Order user id:  "+order.getUser()+"\n Order book id:  "+bookList+"\n Order cancellation status:  "+order.isCancel());
            return order;
        }
        return null;
    }
    @Override
    public Order placeOrder(OrderDTO orderDTO){
        Optional<User> user=userRepo.findById(orderDTO.getUser());
        List<Book> bookList=orderDTO.getBook().stream().map(book->bookRepo.findById(book).get()).collect(Collectors.toList());
        long price=bookList.stream().mapToLong(n->(n.getPrice())).sum();
        long quantity=bookList.stream().mapToLong(n->(n.getQuantity())).sum();
        if (user.isPresent()){
            Long cartUser=cartRepo.findByCartUser(orderDTO.getUser());
            cartRepo.deleteById(cartUser);
            Order order=new Order(user.get(),bookList,price,quantity,orderDTO.getAddress(), orderDTO.getCancel());
            orderRepo.save(order);
            emailSenderService.sendEmail(user.get().getEmail(), "Order placed!","Hii...."+user.get().getFirstName()+" ! \n\n Your order has been placed successfully! Order details are below: \n\n Order id:  "+order.getOrderId()+"\n\n Order date:  "+order.getLocalDate()+"\n\n Order Price:  "+price+"\n Order quantity:  "+quantity+"\n Order address:  "+order.getAddress()+"\n Order user id:  "+order.getUser()+"\n Order book id:  "+bookList+"\n Order cancellation status:  "+order.isCancel());
            return order;
        }
        return null;
    }

    @Override
    public List<Order> getAll(){
        List<Order> orderList=orderRepo.findAll();
        return orderList;
    }
    //Getting particular order details by id
    @Override
    public Order getById(long id){
        Optional<Order> order=orderRepo.findById(id);
        if(order.isPresent()){
            Order orderData=orderRepo.findById(id).get();
            return orderData;
        }else {
            throw new OrderException("Can not find order id: "+id);
        }
    }

    //Updating particular order by id
    @Override
    public Order updateById(long id, OrderDTO orderDTO){
        Optional<User> user=userRepo.findById(orderDTO.getUser());
        List<Book> bookList=orderDTO.getBook().stream().map(book->bookRepo.findById(book).get()).collect(Collectors.toList());
        Order order=orderRepo.findById(id).get();
        if (orderRepo.findById(id).isPresent() && user.isPresent()){

            order.setAddress(orderDTO.getAddress());
            order.setUser(user.get());
            order.setBook(bookList.stream().map(book -> bookRepo.findById(book.getBookId()).get()).collect(Collectors.toList()));
            order.setCancel(orderDTO.getCancel());
            long price=bookList.stream().mapToLong(n->(n.getPrice())).sum();
            long quantity=bookList.stream().mapToLong(n-> (n.getQuantity())).sum();
            orderRepo.save(order);
            String token=tokenUtil.createToken(order.getOrderId());
            emailSenderService.sendEmail(user.get().getEmail(), "Order updated successfully!","Hii...."+user.get().getFirstName()+" ! \n\n Your order has been updated successfully! Order details are below: \n\n Order id:  "+order.getOrderId()+"\n Order date:  "+ order.getLocalDate()+"\n Order Price:  "+price+"\n Order quantity:  "+quantity+"\n Order address:  "+order.getAddress()+"\n Order user id:  "+order.getUser()+"\n Order book id:  "+bookList+"\n Order cancellation status:  "+order.isCancel());
            return order;
        }else {
            throw new OrderException("Data not found");
        }
    }

   @Override
    public String cancelOrder(Long orderId, Long userId) {
        User user = userRepo.findById(userId).orElse(null);
        if (user != null) {
            Order order = orderRepo.findById(orderId).orElse(null);
            if (order != null) {
                order.setCancel(true);
                Book book = bookRepo.findById(userId).orElse(null);
                book.setQuantity(book.getQuantity() + order.getQuantity());
                emailSenderService.sendEmail(user.getEmail(),"Order Cancelled","Order Id " + orderId+"\n"+order);
                orderRepo.save(order);
                return "Order Cancelled";
            }
        }
        return "User Not Found !!";
    }

    //Deleting particular order details by id
    @Override
    public String deleteById(long orderId){
        Optional<Order> order=orderRepo.findById(orderId);
        //Optional<User> user = userRepo.findById(userId);
        if(order.isPresent()){
            orderRepo.deleteById(orderId);
            //emailSenderService.sendEmail(user.get().getEmail(), "Order is deleted successfully!","Hii...."+user.get().getFirstName()+" ! \n\n Your order has been deleted successfully! Order id: "+order.get().getOrderId());
            return "Details deleted successfully!";
        }else {
            throw new OrderException("Data not found: "+orderId);
        }
    }


}