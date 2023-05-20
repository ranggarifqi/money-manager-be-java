package com.ranggarifqi.moneymanager.user.services;

import com.ranggarifqi.moneymanager.common.email.IEmailService;
import com.ranggarifqi.moneymanager.common.exception.ForbiddenException;
import com.ranggarifqi.moneymanager.common.exception.UnauthorizedException;
import com.ranggarifqi.moneymanager.common.jwt.IJWTService;
import com.ranggarifqi.moneymanager.model.User;
import com.ranggarifqi.moneymanager.user.IUserRepository;
import com.ranggarifqi.moneymanager.user.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.Timestamp;
import java.time.Clock;
import java.util.Date;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class UserServiceSignInTests {
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

    private final String email = "test@ranggarifqi.com";
    private final String password = "somePassword";

    @BeforeEach
    void beforeEach() {
        userService = new UserService(this.userRepository, this.passwordEncoder, this.emailService, this.clock, this.jwtService);
    }

    @Test
    public void shouldThrowUnauthorizedOnEmailNotFound() {
        Mockito.when(this.userRepository.findByEmail(email)).thenReturn(null);

        Exception error = null;

        try {
            this.userService.signIn(this.email, this.password);
        } catch (Exception e) {
            error = e;
        }

        Assertions.assertNotNull(error);
        Assertions.assertInstanceOf(UnauthorizedException.class, error);

        Assertions.assertEquals("Invalid email or password", error.getMessage());

        Mockito.verify(this.userRepository, Mockito.times(1)).findByEmail(ArgumentMatchers.anyString());
        Mockito.verify(this.passwordEncoder, Mockito.times(0)).matches(ArgumentMatchers.anyString(), ArgumentMatchers.anyString());
        Mockito.verify(this.jwtService, Mockito.times(0)).generate(ArgumentMatchers.anyString(), ArgumentMatchers.anyString(), ArgumentMatchers.anyString());
    }

    @Test
    public void shouldThrowUnauthorizedOnPasswordMismatch() {
        User existingUser = new User();
        existingUser.setId(UUID.randomUUID());
        existingUser.setEmail(this.email);
        existingUser.setPassword("someOtherPassword");

        Mockito.when(this.userRepository.findByEmail(this.email)).thenReturn(existingUser);
        Mockito.when(this.passwordEncoder.matches(this.password, existingUser.getPassword())).thenReturn(false);

        Exception error = null;

        try {
            this.userService.signIn(this.email, this.password);
        } catch (Exception e) {
            error = e;
        }

        Assertions.assertNotNull(error);
        Assertions.assertInstanceOf(UnauthorizedException.class, error);

        Assertions.assertEquals("Invalid email or password", error.getMessage());

        Mockito.verify(this.userRepository, Mockito.times(1)).findByEmail(ArgumentMatchers.anyString());
        Mockito.verify(this.passwordEncoder, Mockito.times(1)).matches(ArgumentMatchers.anyString(), ArgumentMatchers.anyString());
        Mockito.verify(this.jwtService, Mockito.times(0)).generate(ArgumentMatchers.anyString(), ArgumentMatchers.anyString(), ArgumentMatchers.anyString());
    }

    @Test
    public void shouldThrowForbiddenExceptionIfUserIsNotVerified() {
        User existingUser = new User();
        existingUser.setId(UUID.randomUUID());
        existingUser.setEmail(this.email);
        existingUser.setPassword(this.password);

        Mockito.when(this.userRepository.findByEmail(this.email)).thenReturn(existingUser);
        Mockito.when(this.passwordEncoder.matches(this.password, existingUser.getPassword())).thenReturn(true);

        Exception error = null;

        try {
            this.userService.signIn(this.email, this.password);
        } catch (Exception e) {
            error = e;
        }

        Assertions.assertNotNull(error);
        Assertions.assertInstanceOf(ForbiddenException.class, error);

        Assertions.assertEquals("Please verify your account", error.getMessage());

        Mockito.verify(this.userRepository, Mockito.times(1)).findByEmail(ArgumentMatchers.anyString());
        Mockito.verify(this.passwordEncoder, Mockito.times(1)).matches(ArgumentMatchers.anyString(), ArgumentMatchers.anyString());
        Mockito.verify(this.jwtService, Mockito.times(0)).generate(ArgumentMatchers.anyString(), ArgumentMatchers.anyString(), ArgumentMatchers.anyString());
    }

    @Test
    public void shouldReturnJWTString() {
        User existingUser = new User();
        existingUser.setId(UUID.randomUUID());
        existingUser.setEmail(this.email);
        existingUser.setPassword("someOtherPassword");
        existingUser.setVerifiedAt(new Timestamp(new Date().getTime()));

        Mockito.when(this.userRepository.findByEmail(this.email)).thenReturn(existingUser);
        Mockito.when(this.passwordEncoder.matches(this.password, existingUser.getPassword())).thenReturn(true);
        Mockito.when(this.jwtService.generate(existingUser.getId().toString(), existingUser.getEmail(), existingUser.getAccessLevel())).thenReturn("SomeJWTString");

        Exception error = null;
        String result = null;

        try {
            result = this.userService.signIn(this.email, this.password);
        } catch (Exception e) {
            error = e;
        }

        Assertions.assertNull(error);
        Assertions.assertEquals("SomeJWTString", result);

        Mockito.verify(this.userRepository, Mockito.times(1)).findByEmail(ArgumentMatchers.anyString());
        Mockito.verify(this.passwordEncoder, Mockito.times(1)).matches(ArgumentMatchers.anyString(), ArgumentMatchers.anyString());
        Mockito.verify(this.jwtService, Mockito.times(1)).generate(ArgumentMatchers.anyString(), ArgumentMatchers.anyString(), ArgumentMatchers.anyString());
    }
}
