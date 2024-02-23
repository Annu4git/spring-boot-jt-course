package com.anurag.CourseService.handler;

import com.anurag.CourseService.dto.ServiceResponse;
import com.anurag.CourseService.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ServiceResponse<?> handleResourceNotFoundException(ResourceNotFoundException e) {
        ServiceResponse<?> serviceResponse = new ServiceResponse<>();
        serviceResponse.getErrorMessages().add(e.getField() + " not found with value : " + e.getValue());
        serviceResponse.setHttpStatus(HttpStatus.NOT_FOUND);
        return serviceResponse;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ServiceResponse<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        ServiceResponse<?> serviceResponse = new ServiceResponse<>();
        serviceResponse.setErrorMessages(new ArrayList<>());
        for(ObjectError error : e.getBindingResult().getAllErrors()) {
            StringBuilder sb = new StringBuilder();
            sb.append(((FieldError)error).getField()).append(" : ").append(error.getDefaultMessage());
            serviceResponse.getErrorMessages().add(sb.toString());
        }
        serviceResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        return serviceResponse;
    }
}
