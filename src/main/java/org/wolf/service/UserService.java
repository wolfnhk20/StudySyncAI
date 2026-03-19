package org.wolf.service;

import org.wolf.dto.RegisterRequest;
import org.wolf.model.User;
import org.wolf.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(RegisterRequest req) {
        if (userRepository.existsByEmail(req.getEmail())) {
            throw new IllegalArgumentException("Email already registered: " + req.getEmail());
        }
        User user = new User(req.getName(), req.getEmail(),
                             passwordEncoder.encode(req.getPassword()));
        return userRepository.save(user);
    }

    public List<User> getAllUsers()                    { return userRepository.findAll(); }
    public Optional<User> getUserById(Long id)         { return userRepository.findById(id); }
    public Optional<User> getUserByEmail(String email) { return userRepository.findByEmail(email); }
    public void deleteUser(Long id)                    { userRepository.deleteById(id); }
}
