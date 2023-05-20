package com.ranggarifqi.moneymanager.user;

import com.ranggarifqi.moneymanager.model.User;

import java.util.UUID;

public interface IUserRepository {
    User findByEmail(String email);

    User findByVerifyToken(String token);

    User getReferenceById(UUID userId);

    void create(User payload);

    void update(User payload);
}
