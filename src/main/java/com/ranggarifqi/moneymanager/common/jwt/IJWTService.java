package com.ranggarifqi.moneymanager.common.jwt;

public interface IJWTService {
    public String generate(String userId, String email);
}
