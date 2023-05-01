package com.ranggarifqi.moneymanager.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class SignInDTO {

    @NotBlank(message = "email is mandatory")
    @Email(message = "must be a valid email")
    private String email;

    @NotBlank(message = "password is mandatory")
    private String password;

    public SignInDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
