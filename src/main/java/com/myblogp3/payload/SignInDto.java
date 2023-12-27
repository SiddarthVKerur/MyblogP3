package com.myblogp3.payload;

import lombok.Data;

@Data
public class SignInDto {
    private String usernameOrEmail;
    private String password;
}
