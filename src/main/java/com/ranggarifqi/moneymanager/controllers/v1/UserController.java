package com.ranggarifqi.moneymanager.controllers.v1;

import com.ranggarifqi.moneymanager.common.response.ErrorResponse;
import com.ranggarifqi.moneymanager.model.User;
import com.ranggarifqi.moneymanager.user.IUserService;
import com.ranggarifqi.moneymanager.user.dto.SignUpDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/v1/users")
public class UserController {

    private final IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/signup")
    public User signUp(@Valid @RequestBody SignUpDTO payload) {
        try {
            return this.userService.signUp(payload);
        } catch (Exception e) {
            throw ErrorResponse.construct(e);
        }
    }
}
