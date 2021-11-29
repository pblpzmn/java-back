package com.react.java.project.react.user;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
@Entity
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull(message = "{com.react.java.project.react.user.userName}")
    @Size(min=4, max =10)
    @UniqueUserName
    private String userName;
    
    @NotNull(message = "{com.react.java.project.react.user.displayName}")
    @Size(min=4, max =16)
    private String displayName;
    
    @NotNull(message = "{com.react.java.project.react.user.password}")
    @Size(min=4, max =255)
    private String password;
}
