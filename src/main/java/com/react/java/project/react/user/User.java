package com.react.java.project.react.user;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class User {

    @Id
    @GeneratedValue
    private Long id;
    private String userName;
    private String displayName;
    private String password;
}
