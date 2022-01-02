package com.react.java.project.react.user;

import javax.validation.Valid;

import com.react.java.project.react.common.BaseController;
import com.react.java.project.react.common.GenericResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
// @Validated
public class UserController extends BaseController{
    
    @Autowired
    UserService userService;

    @PostMapping("/api/1.0/users")
    GenericResponse createUser( @Valid @RequestBody User user){
        userService.save(user);
        return new GenericResponse("User saved");
    }
}
