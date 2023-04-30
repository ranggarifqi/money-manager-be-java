package com.ranggarifqi.moneymanager.user;

import com.ranggarifqi.moneymanager.model.User;

public interface IUserRepository {
    User findByEmail(String email);

    User findByVerifyToken(String token);

    void create(User payload);
}
