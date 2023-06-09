package com.ranggarifqi.moneymanager.user;

import com.ranggarifqi.moneymanager.model.User;
import com.ranggarifqi.moneymanager.user.dto.SignUpDTO;

public interface IUserService {
    User signUp(SignUpDTO payload);

    User verifyUser(String token);

    String signIn(String email, String password);
}
