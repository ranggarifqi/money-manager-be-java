package com.ranggarifqi.moneymanager.common.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.util.Date;

@Service
public class JWTService implements IJWTService{

    @Value("${jwt.cookieName}")
    private String cookieName;

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expirationMs}")
    private int expirationMs;

    private final Clock clock;

    @Autowired
    public JWTService(Clock clock) {
        this.clock = clock;
    }

    @Override
    public String generate(String userId, String email) {
        Date now = Date.from(this.clock.instant());

        return Jwts.builder()
                .setSubject(email)
                .claim("userId", userId)
                .claim("email", email)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + this.expirationMs))
                .signWith(SignatureAlgorithm.HS512, this.secret)
                .compact();
    }
}
