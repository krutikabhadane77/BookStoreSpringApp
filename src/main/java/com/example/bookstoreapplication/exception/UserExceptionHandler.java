package com.example.bookstoreapplication.exception;

import com.example.bookstoreapplication.dto.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class UserExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDTO> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception){
        List<ObjectError> errorList=exception.getBindingResult().getAllErrors();
        List<String> errMsg=errorList.stream()
                .map(objectError -> objectError.getDefaultMessage())
                .collect(Collectors.toList());
        ResponseDTO responseUserDTO =new ResponseDTO("Exception while processing REST request", errMsg.toString());
        return new ResponseEntity<>(responseUserDTO, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(UserException.class)
    public ResponseEntity<ResponseDTO> handlePayrollException(UserException exception){
        ResponseDTO responseUserDTO =new ResponseDTO("Exception while processing REST request",exception.getMessage());
        return new ResponseEntity<>(responseUserDTO, HttpStatus.BAD_GATEWAY);
    }
}