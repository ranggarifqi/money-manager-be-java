package com.ranggarifqi.moneymanager.controllers.v1;

import com.ranggarifqi.moneymanager.common.response.APIResponse;
import com.ranggarifqi.moneymanager.common.response.ErrorResponse;
import com.ranggarifqi.moneymanager.model.User;
import com.ranggarifqi.moneymanager.user.IUserService;
import com.ranggarifqi.moneymanager.user.dto.SignUpDTO;
import com.ranggarifqi.moneymanager.user.dto.SignUpResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/users")
public class UserController {

    private final IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/signup", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIResponse<SignUpResponse>> signUp(@Valid @RequestBody SignUpDTO payload) {
        try {
            User newUser = this.userService.signUp(payload);
            SignUpResponse responsePayload = new SignUpResponse(newUser.getId(), newUser.getName(), newUser.getEmail(), newUser.getPhone());
            return APIResponse.constructResponse(responsePayload);
        } catch (Exception e) {
            throw ErrorResponse.construct(e);
        }
    }
}
