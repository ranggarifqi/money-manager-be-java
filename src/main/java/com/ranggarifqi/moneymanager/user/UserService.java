package com.ranggarifqi.moneymanager.user;

import com.ranggarifqi.moneymanager.common.exception.ConflictException;
import com.ranggarifqi.moneymanager.model.User;
import com.ranggarifqi.moneymanager.user.dto.SignUpDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService{

    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(IUserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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

        // Save the user
        User newUser = new User(payload.name(), payload.email(), payload.phone(), password, null, null);
        this.userRepository.create(newUser);

        // TODO: send verification email
        return newUser;
    }
}
