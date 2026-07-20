package com.levirazey.launchtracker.service;

import com.levirazey.launchtracker.dto.RegisterRequest;
import com.levirazey.launchtracker.entity.User;
import com.levirazey.launchtracker.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("Username is already taken!");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("That email is already associated with an account!");
        }
        String passwordHash = passwordEncoder.encode(request.getPassword());
        User user = new User(request.getUsername(), request.getEmail(), passwordHash);

        return userRepository.save(user);
    }
}
