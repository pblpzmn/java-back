package com.react.java.project.react;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GenericResponse {
    private String message;

    public GenericResponse(String message){
        this.message = message;
    }

}
