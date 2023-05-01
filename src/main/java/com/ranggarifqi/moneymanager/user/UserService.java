package com.ranggarifqi.moneymanager.user;

import com.ranggarifqi.moneymanager.common.email.IEmailService;
import com.ranggarifqi.moneymanager.common.exception.ConflictException;
import com.ranggarifqi.moneymanager.common.exception.NotFoundException;
import com.ranggarifqi.moneymanager.common.exception.UnauthorizedException;
import com.ranggarifqi.moneymanager.common.jwt.IJWTService;
import com.ranggarifqi.moneymanager.model.User;
import com.ranggarifqi.moneymanager.user.dto.SignUpDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Clock;

@Service
public class UserService implements IUserService{

    Logger logger = LoggerFactory.getLogger(UserService.class);

    @Value("${url.baseUrl}")
    private String baseUrl;

    @Value("${url.userVerificationPath}")
    private String userVerificationPath;

    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final IEmailService emailService;

    private final IJWTService jwtService;

    private final Clock clock;

    @Autowired
    public UserService(IUserRepository userRepository, PasswordEncoder passwordEncoder, IEmailService emailService, Clock clock, IJWTService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
        this.clock = clock;
        this.jwtService = jwtService;
    }

    @Override
    public User signUp(SignUpDTO payload) {
        // Find if email exists
        User existingUser = this.userRepository.findByEmail(payload.email());
        if (existingUser != null) {
            throw new ConflictException("User with email " + payload.email() + " already exists");
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

    @Override
    public User verifyUser(String token) {
        User user = this.userRepository.findByVerifyToken(token);
        if (user == null) {
            throw new NotFoundException("Invalid verify token");
        }

        Timestamp verifiedAt = new Timestamp(this.clock.millis());

        user.setVerifiedAt(verifiedAt);
        user.setVerifyToken(null);

        this.userRepository.update(user);

        return user;
    }

    @Override
    public String signIn(String email, String password) {
        User existingUser = this.userRepository.findByEmail(email);
        if (existingUser == null) {
            throw new UnauthorizedException("Invalid email or password");
        }

        boolean isPasswordMatching = this.passwordEncoder.matches(password, existingUser.getPassword());
        if (!isPasswordMatching) {
            throw new UnauthorizedException("Invalid email or password");
        }

        return this.jwtService.generate(existingUser.getId().toString(), existingUser.getEmail());
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public void setUserVerificationPath(String userVerificationPath) {
        this.userVerificationPath = userVerificationPath;
    }
}
