package com.yash.userservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<APIStatus> handlerResourceNotFoundException(ResourceNotFoundException exception){
        String message = exception.getMessage();
        APIStatus status = APIStatus.builder().status(HttpStatus.NOT_FOUND).message(message).success(true).build();
        return new ResponseEntity<>(status,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<APIStatus>> handlerMethodArgumentNotValidException(MethodArgumentNotValidException exception){
        List<APIStatus> list = new ArrayList<>();

        exception.getBindingResult().getFieldErrors().forEach(fieldError -> {
            APIStatus status = new APIStatus();
            status.setStatus(HttpStatus.BAD_REQUEST);
            status.setMessage(fieldError.getDefaultMessage());
            status.setSuccess(false);
            list.add(status);
        });
        return new ResponseEntity<>(list,HttpStatus.BAD_REQUEST);
    }
}
