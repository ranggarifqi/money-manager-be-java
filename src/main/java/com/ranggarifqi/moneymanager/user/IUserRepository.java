package com.ranggarifqi.moneymanager.user;

import com.ranggarifqi.moneymanager.model.User;

public interface IUserRepository {
    User findByEmail(String email);

    User findByVerifyToken(String token);

    User getReferenceById(String userId);

    void create(User payload);

    void update(User payload);
}
