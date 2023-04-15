package com.ranggarifqi.moneymanager.user;

import com.ranggarifqi.moneymanager.common.email.IEmailService;
import com.ranggarifqi.moneymanager.common.exception.ConflictException;
import com.ranggarifqi.moneymanager.model.User;
import com.ranggarifqi.moneymanager.user.dto.SignUpDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService{

    @Value("${url.baseUrl}")
    private String baseUrl;

    @Value("${url.userVerificationPath}")
    private String userVerificationPath;

    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final IEmailService emailService;

    @Autowired
    public UserService(IUserRepository userRepository, PasswordEncoder passwordEncoder, IEmailService emailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    @Override
    public User signUp(SignUpDTO payload) {
        // Find if email exists
        User existingUser = this.userRepository.findByEmail(payload.email());
        if (existingUser != null) {
            throw new ConflictException("User with email" + payload.email() + " already exists");
        }

        // Encrypt the password
        String password = passwordEncoder.encode(payload.rawPassword());

        String verifyToken = passwordEncoder.encode(payload.name() + "_" + payload.email() + "_" + payload.phone());

        // Save the user
        User newUser = new User(payload.name(), payload.email(), payload.phone(), password, verifyToken, null, null, null);
        userRepository.create(newUser);

        String subject = "User registered successfully";
        String htmlBody = "<p>Click this <a href='"+ this.baseUrl + this.userVerificationPath +"?token="+ verifyToken +"'>link</a> to verify your account</p>";

        this.emailService.sendSimpleEmail(newUser.getEmail(), subject, htmlBody);

        return newUser;
    }
}
