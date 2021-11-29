package com.react.java.project.react;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.List;

import com.react.java.project.react.error.ApiError;
import com.react.java.project.react.user.User;
import com.react.java.project.react.user.UserRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class UserControllerTest {
    
    private final String API_1_0_USERS = "/api/1.0/users";
    @Autowired
    TestRestTemplate testRestTemplate;
    
    @Autowired
    UserRepository userRepository;


    @Before
    public void cleanUpDb(){
        userRepository.deleteAll();
    }
    
    private User createValidUser() {
        User user =  new User();
        user.setUserName("test-user");
        user.setDisplayName("display");
        user.setPassword("P4ssword");
        return user;
    }
    
    private <T>ResponseEntity<T> postSignUp(Object request, Class<T> response){
        return testRestTemplate.postForEntity(API_1_0_USERS, request, response);
    }

    @Test
    public void postUser_wheUserIsValid_receiveOk(){
        User user = createValidUser();
        ResponseEntity<Object> response =  this.postSignUp(user, Object.class);
        assertEquals(  HttpStatus.OK , response.getStatusCode());
    }

    @Test
    public void postUser_whenUserIsValid_userSavedToDatabase(){
        User user = createValidUser();
        postSignUp(user, Object.class);
        assertEquals(1,  userRepository.count() );
    }

    @Test
    public void postUser_wheUserIsValid_receiveSuccessMessage(){
        User user = createValidUser();
        ResponseEntity<Object> response =  postSignUp(user, Object.class);
        assertEquals(  HttpStatus.OK , response.getStatusCode());
    }

    @Test
    public void postUser_wheUserIsValid_encryptPassword(){
        User user = createValidUser();
        postSignUp(user, Object.class);
        List<User> users = userRepository.findAll();
        assertNotEquals(  "P4ssword", users.get(0).getPassword() );
    }

    @Test
    public void postUserName_whenNull_returnBadRequest(){
        User user = createValidUser();
        user.setUserName(null);
        ResponseEntity<Object> response =  this.postSignUp(user, Object.class);
        assertEquals(  HttpStatus.BAD_REQUEST , response.getStatusCode() );
    }


    @Test
    public void postDisplayName_whenNull_returnBadRequest(){
        User user = createValidUser();
        user.setDisplayName(null);
        ResponseEntity<Object> response =  this.postSignUp(user, Object.class);
        assertEquals(  HttpStatus.BAD_REQUEST , response.getStatusCode() );
    }

    @Test
    public void postPassword_whenNull_returnBadRequest(){
        User user = createValidUser();
        user.setPassword(null);
        ResponseEntity<Object> response =  this.postSignUp(user, Object.class);
        assertEquals(  HttpStatus.BAD_REQUEST , response.getStatusCode() );
    }

    @Test
    public void postUser_whenIvalid_returnApierror(){
        User user = createValidUser();
        user.setPassword(null);
        ResponseEntity<ApiError> response =  this.postSignUp(user, ApiError.class);
        assertEquals(  this.API_1_0_USERS , response.getBody().getUrl() );
    }

    @Test
    public void postUser_whenIvalid_returnApierrorWithSpecificErrorUserName(){
        User user = createValidUser();
        user.setUserName(null);
        ResponseEntity<ApiError> response =  this.postSignUp(user, ApiError.class);
        assertEquals( "User name cannot be null" , response.getBody().getValidationErrors().get("userName") );
    }

    @Test
    public void postUser_whenIvalid_returnApierrorWithSpecificErrorDisplayName(){
        User user = createValidUser();
        user.setPassword(null);
        ResponseEntity<ApiError> response =  this.postSignUp(user, ApiError.class);
        assertEquals( "Password cannot be null" , response.getBody().getValidationErrors().get("password") );
    }
}
