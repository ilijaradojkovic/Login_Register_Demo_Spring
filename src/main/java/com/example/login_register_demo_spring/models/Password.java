package com.example.login_register_demo_spring.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Password {
    private String password;
    private String matchingPassword;

    private String email;
    private String username;
    private String token;

}
