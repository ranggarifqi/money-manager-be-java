package com.ranggarifqi.moneymanager.user.services;

import com.ranggarifqi.moneymanager.common.email.IEmailService;
import com.ranggarifqi.moneymanager.common.exception.ConflictException;
import com.ranggarifqi.moneymanager.model.User;
import com.ranggarifqi.moneymanager.user.IUserRepository;
import com.ranggarifqi.moneymanager.user.UserService;
import com.ranggarifqi.moneymanager.user.dto.SignUpDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Clock;

@ExtendWith(MockitoExtension.class)
public class UserServiceSignupTests {

    @Mock
    private IUserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private IEmailService emailService;

    @Mock
    private Clock clock;

    private UserService userService;

    @BeforeEach
    void beforeEach() {
        userService = new UserService(this.userRepository, this.passwordEncoder, emailService, clock);
        userService.setBaseUrl("http://test.com");
        userService.setUserVerificationPath("/some-path");
    }

    @Test
    public void signUp_shouldThrowConflictExceptionIfEmailExists() {
        Mockito.when(userRepository.findByEmail("test@test.com")).thenReturn(
            new User("Fulan", "test@test.com", "+62000000001", "123", null, null, null, null)
        );

        Exception error = null;

        SignUpDTO payload = new SignUpDTO("New Fulan", "test@test.com", "+62123123213", "password");

        try {
            this.userService.signUp(payload);
        } catch (ConflictException e) {
            error = e;
        }

        Assertions.assertNotNull(error);
        Assertions.assertInstanceOf(ConflictException.class, error);

        Mockito.verify(userRepository, Mockito.times(1)).findByEmail("test@test.com");
        Mockito.verify(passwordEncoder, Mockito.never()).encode("password");
    }

    @Test
    public void signUp_shouldEncodePasswordAndTriggerCreation() {
        Mockito.when(userRepository.findByEmail("test@test.com")).thenReturn(null);
        Mockito.when(passwordEncoder.encode("password")).thenReturn("EncodedPassword");
        Mockito.when(passwordEncoder.encode("New Fulan_test@test.com_+62123123213")).thenReturn("EncodedVerifyToken");

        SignUpDTO payload = new SignUpDTO("New Fulan", "test@test.com", "+62123123213", "password");

        User newUser = userService.signUp(payload);

        Assertions.assertNotNull(newUser);
        Assertions.assertEquals("EncodedPassword", newUser.getPassword());

        Mockito.verify(userRepository, Mockito.times(1)).findByEmail("test@test.com");
        Mockito.verify(passwordEncoder, Mockito.times(1)).encode("password");
        Mockito.verify(emailService, Mockito.times(1)).sendSimpleEmail("test@test.com", "User registered successfully", "<p>Click this <a href='http://test.com/some-path?token=EncodedVerifyToken'>link</a> to verify your account</p>");
    }
}
