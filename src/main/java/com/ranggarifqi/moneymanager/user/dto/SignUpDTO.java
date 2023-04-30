package com.ranggarifqi.moneymanager.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class SignUpDTO {

    @NotBlank(message = "name is mandatory")
    private String name;

    @NotBlank(message = "email is mandatory")
    @Email(message = "must be a valid email")
    private String email;

    @NotBlank(message = "phone number is mandatory")
    @Pattern(regexp = "^\\s*(?:\\+?(\\d{1,3}))?([-. (]*(\\d{3})[-. )]*)?((\\d{3})[-. ]*(\\d{2,4})(?:[-.x ]*(\\d+))?)\\s*$", message = "Must be a valid phone number")
    private String phone;

    @NotBlank(message = "password is mandatory")
    private String rawPassword;

    public SignUpDTO(String name, String email, String phone, String rawPassword) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.rawPassword = rawPassword;
    }

    public String name() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String email() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String phone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String rawPassword() {
        return rawPassword;
    }

    public void setRawPassword(String rawPassword) {
        this.rawPassword = rawPassword;
    }
}
