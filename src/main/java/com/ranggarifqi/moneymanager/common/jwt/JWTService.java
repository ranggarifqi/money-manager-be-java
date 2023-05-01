package com.ranggarifqi.moneymanager.common.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Clock;
import java.util.Date;
import java.util.function.Function;

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
                .signWith(this.getSignInKey())
                .compact();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(this.secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

}
