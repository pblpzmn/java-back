package com.react.java.project.react;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.List;

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
    
    private static final String API_1_0_USERS = "/api/1.0/users";
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
    
    @Test
    public void postUser_wheUserIsValid_receiveOk(){
        User user = createValidUser();
        ResponseEntity<Object> response =  testRestTemplate.postForEntity(API_1_0_USERS, user, Object.class);
        assertEquals( response.getStatusCode() , HttpStatus.OK );
    }

    @Test
    public void postUser_whenUserIsValid_userSavedToDatabase(){
        User user = createValidUser();
        testRestTemplate.postForEntity(API_1_0_USERS, user, Object.class);
        assertEquals( userRepository.count(), 1);
    }

    @Test
    public void postUser_wheUserIsValid_receiveSuccessMessage(){
        User user = createValidUser();
        ResponseEntity<GenericResponse> response =  testRestTemplate.postForEntity(API_1_0_USERS, user, GenericResponse.class);
        assertEquals( response.getStatusCode() , HttpStatus.OK );
    }

    @Test
    public void postUser_wheUserIsValid_encryptPassword(){
        User user = createValidUser();
        testRestTemplate.postForEntity(API_1_0_USERS, user, Object.class);
        List<User> users = userRepository.findAll();
        assertNotEquals(users.get(0).getPassword() , "P4ssword" );
    }

}
