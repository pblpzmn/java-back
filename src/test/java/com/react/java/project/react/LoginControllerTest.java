package com.react.java.project.react;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.react.java.project.react.error.ApiError;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class LoginControllerTest{

    private static final String API_LOGIN = "/api/1.0/login";

    @Autowired
    TestRestTemplate testRestTemplate; 

    @Test
    public void popstLogin_witoutUserCredential_receiveUnauthorized(){
        ResponseEntity<Object> response = login(Object.class);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    public void popstLogin_witoutIncorrectUserCredential_receiveUnauthorized(){
        authenticate();
        ResponseEntity<Object> response = login(Object.class);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    public void popstLogin_witoutUserCredential_receiveApiErro(){
        ResponseEntity<ApiError> response = login(ApiError.class);
        assertEquals(API_LOGIN, response.getBody().getUrl());
    }

    private void authenticate(){
        testRestTemplate.getRestTemplate().getInterceptors().add(new BasicAuthenticationInterceptor("username", "password"));
    }

    public <T> ResponseEntity<T> login(Class<T> responseType){
        return testRestTemplate.postForEntity(API_LOGIN, null, responseType);
    }

}