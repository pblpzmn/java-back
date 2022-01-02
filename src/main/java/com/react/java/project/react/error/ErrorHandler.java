package com.react.java.project.react.error;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.error.ErrorAttributeOptions.Include;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;


@RestController
public class ErrorHandler implements ErrorController{
    
    @Autowired
    ErrorAttributes errorAttributes;

    @RequestMapping("/error")
    ApiError handleError(WebRequest serverRequest){
        Map<String, Object> attributes = errorAttributes.getErrorAttributes(serverRequest, ErrorAttributeOptions.of(Include.MESSAGE));
        String message = (String)attributes.get("message");
        String url = (String)attributes.get("path");
        int status = (Integer)attributes.get("status");
        return new ApiError(status,message, url);
    }

    
    public String getErrorPath(){
        return "/error";
    }

}
