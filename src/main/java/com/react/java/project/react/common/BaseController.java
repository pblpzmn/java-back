package com.react.java.project.react.common;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.react.java.project.react.error.ApiError;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

public class BaseController {
    
    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ApiError handleValidationException(MethodArgumentNotValidException exception, HttpServletRequest request){
        ApiError api = new ApiError(404, "invalid", request.getServletPath());
        Map<String, String> validationErrors = new HashMap<>();
        BindingResult result = exception.getBindingResult(); 
        for (FieldError error : result.getFieldErrors() ) {
            validationErrors.put(error.getField(), error.getDefaultMessage());
        }
        api.setValidationErrors(validationErrors);
        return api;
    }

}
