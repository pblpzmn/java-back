package com.react.java.project.react;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.react.java.project.react.user.User;
import com.react.java.project.react.user.UserRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
public class UserRepositoryTest {
    
    @Autowired
    TestEntityManager testEntityManager;


    @Autowired
    UserRepository userRepository;


    @Test
    public void findUserByname_whenUserExist_returnUser(){
        User user = new User();
        user.setDisplayName("displayName");
        user.setUserName("userName");
        userRepository.findUserByUserName(user.getUserName());
        testEntityManager.persist(user);

        userRepository.findUserByUserName(user.getUserName());

        assertNotNull(userRepository.findUserByUserName(user.getUserName()));

    }
    
    @Test
    public void findUserByname_whenUserNotExist_returnNull(){
        assertNull(userRepository.findUserByUserName( "notExistingUser") );
    }
}
