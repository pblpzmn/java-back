package com.react.java.project.react.user;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

public class UniqueUserValidator implements ConstraintValidator<UniqueUserName, String>{
    
    @Autowired
    UserRepository userRepository;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context){
        if (userRepository.findUserByUserName(value) == null){
            return true;
        }
        return false;
    }

}
