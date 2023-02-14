package com.example.bookstoreapplication.controller;

import com.example.bookstoreapplication.dto.LoginDTO;
import com.example.bookstoreapplication.dto.ResponseDTO;
import com.example.bookstoreapplication.dto.UserDTO;
import com.example.bookstoreapplication.model.User;
import com.example.bookstoreapplication.service.IUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    IUserService userService;

    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> register(@Valid @RequestBody UserDTO userDTO) throws Exception{
        User user=userService.register(userDTO);
        ResponseDTO responseUserDTO =new ResponseDTO("User details is submitted!",user);
        return new ResponseEntity<>(responseUserDTO, HttpStatus.CREATED);
    }
    //Create Api for Getting all user details present in the database
    @GetMapping("/getAll")
    public ResponseEntity<ResponseDTO> getAll(){
        List<User> user=userService.getAll();
        ResponseDTO responseUserDTO =new ResponseDTO("Getting user details are successfully!",user);
        return new ResponseEntity<>(responseUserDTO,HttpStatus.FOUND);
    }

    @GetMapping("/getByUserId/{id}")
    public ResponseEntity<ResponseDTO> getByUserId(@PathVariable Long id){
        Optional<User> user=userService.getByUserId(id);
        ResponseDTO responseUserDTO =new ResponseDTO("Getting user details by id is successfully!",user);
        return new ResponseEntity<>(responseUserDTO,HttpStatus.FOUND);
    }


    @PutMapping("/forgotPassword/{email}")
    public ResponseEntity<ResponseDTO> forgotPassword(@Valid @RequestBody UserDTO userDTO, @PathVariable String email){
        User user=userService.forgotPassword(userDTO,email);
        ResponseDTO responseUserDTO =new ResponseDTO("Password has been changed successfully!",user);
        return new ResponseEntity<>(responseUserDTO,HttpStatus.ACCEPTED);
    }

    @PutMapping("/updateUserByEmail/{email}")
    public ResponseEntity<ResponseDTO> updateUserByEmail(@Valid @RequestBody UserDTO userDTO, @PathVariable String email){
        User user=userService.updateUserByEmail(userDTO,email);
        ResponseDTO responseUserDTO =new ResponseDTO("User details has been updated successfully!",user);
        return new ResponseEntity<>(responseUserDTO,HttpStatus.OK);
    }

    @GetMapping("/loginCheck")
    public ResponseEntity<ResponseDTO> loginCheck(@RequestParam(value = "email",defaultValue = "")String email, @RequestParam(value = "password",defaultValue = "")String password){
        String result=userService.loginCheck(email,password);
        ResponseDTO responseUserDTO =new ResponseDTO("Login successfully!!",result);
        return new ResponseEntity<>(responseUserDTO,HttpStatus.ACCEPTED);
    }
    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> login(@Valid @RequestBody LoginDTO loginDTO){
        String result=userService.login(loginDTO);
        ResponseDTO responseUserDTO=new ResponseDTO("Login successfully!!",result);
        return  new ResponseEntity<>(responseUserDTO,HttpStatus.OK);
    }
    @DeleteMapping("/delete/{id}/{token}")
    public ResponseEntity<ResponseDTO> deleteById(@PathVariable Long id,@PathVariable String token){
        userService.deleteById(id,token);
        ResponseDTO responseUserDTO=new ResponseDTO("User details has been deleted successfully!","Deleted user id is: "+id);
        return new ResponseEntity<>(responseUserDTO,HttpStatus.GONE);
    }
    @GetMapping("/getByToken/{token}")
    public ResponseEntity<ResponseDTO> getByToken(@PathVariable String token){
        User user=userService.getByToken(token);
        ResponseDTO responseUserDTO=new ResponseDTO("User found successfully!",user);
        return new ResponseEntity<>(responseUserDTO,HttpStatus.OK);
    }
}