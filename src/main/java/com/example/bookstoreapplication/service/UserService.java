package com.example.bookstoreapplication.service;

import com.example.bookstoreapplication.dto.LoginDTO;
import com.example.bookstoreapplication.dto.UserDTO;
import com.example.bookstoreapplication.exception.UserException;
import com.example.bookstoreapplication.model.User;
import com.example.bookstoreapplication.repository.UserRepository;
import com.example.bookstoreapplication.util.EmailSenderService;
import com.example.bookstoreapplication.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {
    //Create dependency injection for userRepo class
    @Autowired
    UserRepository userRepo;
    //Create dependency injection for tokenUtil class
    @Autowired
    TokenUtil tokenUtil;
    //Create dependency injection for emailSenderService class
    @Autowired
    EmailSenderService emailSenderService;
    //Apply logic for Insert user details in the database

    @Override
    public User register(UserDTO userDTO) throws Exception{
        User user=new User(userDTO);
        String token=tokenUtil.createToken(user.getUserId());
        userRepo.save(user);
        emailSenderService.sendEmail(user.getEmail(),"Registered in Book Store", "Hii...."+user.getFirstName()+"\n You have been successfully registered!\n\n Your registered details are:\n\n User Id:  "+user.getUserId()+"\n First Name:  "+user.getFirstName()+"\n Last Name:  "+user.getLastName()+"\n Email:  "+user.getEmail()+"\n Address:  "+user.getAddress()+"\n DOB:  "+user.getDob()+"\n Token:  "+token);
        return user;
    }
    //Apply logic for Getting all user details present in the database
    @Override
    public List<User> getAll(){
        List<User> list=userRepo.findAll();
        return list;
    }
    //Apply logic for Getting particular user details which will be found by id
    @Override
    public Optional<User> getByUserId(long id){
        Optional<User> user=userRepo.findById(id);
        if (user.isPresent()){
            return user;
        }else {
            throw new UserException("Sorry! We cannot find the user id: "+id);
        }
    }
    //Apply logic for Getting particular user details which will be found by email
    @Override
    public User getByEmail(String email){
        User userList=userRepo.findByEmail(email);
        if(userList==null){
            throw new UserException("Sorry! We can not find the user email: "+email);
        }else {
            return userList;
        }
    }
    //Apply logic for Changing or updating password using id
    @Override
    public User forgotPassword(UserDTO userDTO, String email){
        User user=userRepo.findByEmail(email);
        if (user!=null){
            user.setPassword(userDTO.getPassword());
            userRepo.save(user);
            emailSenderService.sendEmail(user.getEmail(),"Password Updated", "Hii...."+user.getFirstName()+" Your Password has been updated!\n\n Your New password: "+user.getPassword());
            return user;
        }else {
            throw new UserException("Sorry! We can not find the user email: "+email);
        }
    }
    //Create Api for Updating user details by email id
    @Override
    public User updateUserByEmail(UserDTO userDTO, String email){
        User user=userRepo.findByEmail(email);
        if(userRepo.findByEmail(email)!=null) {
            user.setFirstName(userDTO.getFirstName());
            user.setLastName(userDTO.getLastName());
            user.setEmail(userDTO.getEmail());
            user.setAddress(userDTO.getAddress());
            user.setDob(userDTO.getDob());
            user.setPassword(userDTO.getPassword());
            userRepo.save(user);
            emailSenderService.sendEmail(user.getEmail(), "Your details are updated!", "Hii...." + user.getFirstName() + " Your updated details are:\n\n First Name:  " + user.getFirstName() + "\n Last Name:  " + user.getLastName() + "\n Email:  " + user.getEmail() + "\n Address:  " + user.getAddress() + "\n DOB:  " + user.getDob());
            return user;
        }else {
            throw new UserException("Sorry! We can not find entered email: "+email);
        }
    }
    //Apply logic for Check user is logged in with the correct email and password or not
    @Override
    public String loginCheck(String email, String password){
        User user=userRepo.findByEmail(email);
        if(email.equals(user.getEmail()) && password.equals(user.getPassword())){
            emailSenderService.sendEmail(user.getEmail(),"Logged in Successfully!", "Hii...."+user.getFirstName()+"\n\n You have successfully logged in into Book Store App!");
            return "Congratulations!! You have logged in successfully!";

        }else {
            throw new UserException("Sorry! Email or Password is incorrect!");
        }
    }
    @Override
    public String login(LoginDTO loginDTO){
        Optional<User> user= Optional.ofNullable(userRepo.findByEmail(loginDTO.getEmail()));
        if (user.isPresent() && user.get().getPassword().equals(loginDTO.getPassword()) ){
            emailSenderService.sendEmail(user.get().getEmail(),"Logged in Successfully!", "Hii...."+user.get().getFirstName()+"\n\n You have successfully logged in into Book Store App!");
            return "Congratulations!! You have logged in successfully!";
        }else {
            throw new UserException("Sorry! Email or Password is incorrect!");
        }
    }
    @Override
    public void deleteById(long id, String token){
        long userId=tokenUtil.decodeToken(token);
        Optional<User> user=userRepo.findById(id);
        Optional<User> userToken=userRepo.findById(userId);
        if(user.get().getFirstName().equals(userToken.get().getFirstName())){
            userRepo.deleteById(id);
            emailSenderService.sendEmail(user.get().getEmail(), "Order is deleted!","Hii...."+user.get().getFirstName()+" ! \n\n Your order has been deleted successfully! Order id: "+id);
        }else {
            throw new UserException("Sorry! We can not find the user id: "+id);
        }
    }
    @Override
    public User getByToken(String token){
        Long userId=tokenUtil.decodeToken(token);
        Optional<User> user=userRepo.findById(userId);
        if(user.isPresent()){
            return user.get();
        }else {
            throw new UserException("User is not found!");
        }
    }

}