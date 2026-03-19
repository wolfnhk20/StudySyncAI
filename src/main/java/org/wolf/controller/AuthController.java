package org.wolf.controller;

import org.wolf.dto.*;
import org.wolf.model.User;
import org.wolf.security.JwtUtils;
import org.wolf.service.OtpService;
import org.wolf.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final OtpService otpService;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authManager;

    public AuthController(UserService userService, OtpService otpService,
                          JwtUtils jwtUtils, AuthenticationManager authManager) {
        this.userService = userService; this.otpService = otpService;
        this.jwtUtils = jwtUtils; this.authManager = authManager;
    }

    /** POST /auth/register — email + password signup */
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest req) {
        try {
            User saved = userService.registerUser(req);
            String token = jwtUtils.generateToken(saved.getEmail());
            return ResponseEntity.status(HttpStatus.CREATED)
                .body(new AuthResponse(token, saved.getId(), saved.getName(), saved.getEmail()));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        }
    }

    /** POST /auth/login — email + password login */
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest req) {
        try {
            authManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword()));
            User user = userService.getUserByEmail(req.getEmail()).orElseThrow();
            String token = jwtUtils.generateToken(user.getEmail());
            return ResponseEntity.ok(
                new AuthResponse(token, user.getId(), user.getName(), user.getEmail()));
        } catch (AuthenticationException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }
    }

    /** POST /auth/otp/request — send OTP code to email */
    @PostMapping("/otp/request")
    public ResponseEntity<Map<String, String>> requestOtp(@Valid @RequestBody OtpRequest req) {
        otpService.generateAndSend(req.getEmail());
        return ResponseEntity.ok(Map.of(
            "message", "A 6-digit code has been sent to " + req.getEmail(),
            "expiresIn", "10 minutes"));
    }

    /** POST /auth/otp/verify — verify OTP code → receive JWT */
    @PostMapping("/otp/verify")
    public ResponseEntity<?> verifyOtp(@Valid @RequestBody OtpVerifyRequest req) {
        try {
            User user = otpService.verify(req.getEmail(), req.getCode());
            String token = jwtUtils.generateToken(user.getEmail());
            return ResponseEntity.ok(
                new AuthResponse(token, user.getId(), user.getName(), user.getEmail()));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
        }
    }
}
