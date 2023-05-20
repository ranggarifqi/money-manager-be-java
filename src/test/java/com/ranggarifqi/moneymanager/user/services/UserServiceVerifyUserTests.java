package com.ranggarifqi.moneymanager.user.services;

import com.ranggarifqi.moneymanager.common.email.IEmailService;
import com.ranggarifqi.moneymanager.common.exception.NotFoundException;
import com.ranggarifqi.moneymanager.common.jwt.IJWTService;
import com.ranggarifqi.moneymanager.model.User;
import com.ranggarifqi.moneymanager.user.IUserRepository;
import com.ranggarifqi.moneymanager.user.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

@ExtendWith(MockitoExtension.class)
public class UserServiceVerifyUserTests {
    @Mock
    private IUserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private IEmailService emailService;

    @Mock
    private Clock clock;

    @Mock
    private IJWTService jwtService;

    private UserService userService;

    private final String token = "someToken";

    @BeforeEach
    void beforeEach() {
        String instantExpected = "2014-12-22T10:15:30Z";
        this.clock = Clock.fixed(Instant.parse(instantExpected), ZoneId.of("Asia/Jakarta"));

        userService = new UserService(this.userRepository, this.passwordEncoder, this.emailService, this.clock, this.jwtService);
    }

    @Test
    public void verifyUser_shouldThrowNotFound(){
        Mockito.when(this.userRepository.findByVerifyToken(this.token)).thenReturn(null);

        Exception error = null;

        try {
            this.userService.verifyUser(this.token);
        } catch (Exception e) {
            error = e;
        }

        Assertions.assertNotNull(error);
        Assertions.assertInstanceOf(NotFoundException.class, error);
        Assertions.assertEquals("Invalid verify token", error.getMessage());
    }

    @Test
    public void verifyUser_shouldUpdateVerifiedAtOnValidToken() {
        User existingUser = new User("Fulan", "test@test.com", "+62000000001", "123", this.token, null, null, null, "ROLE_USER");

        Mockito.when(this.userRepository.findByVerifyToken(this.token)).thenReturn(existingUser);

        User updatedUser = this.userService.verifyUser(this.token);

        Assertions.assertEquals(this.clock.millis(), updatedUser.getVerifiedAt().getTime());
        Assertions.assertNull(updatedUser.getVerifyToken());
    }
}
