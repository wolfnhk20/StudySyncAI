package org.wolf.service;

import org.wolf.email.EmailService;
import org.wolf.model.OtpToken;
import org.wolf.model.User;
import org.wolf.repository.OtpTokenRepository;
import org.wolf.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;

@Service
@Transactional
public class OtpService {

    private static final int OTP_EXPIRY_MINUTES = 10;
    private final SecureRandom secureRandom = new SecureRandom();

    private final OtpTokenRepository otpRepo;
    private final UserRepository userRepo;
    private final EmailService emailService;

    public OtpService(OtpTokenRepository otpRepo, UserRepository userRepo, EmailService emailService) {
        this.otpRepo = otpRepo; this.userRepo = userRepo; this.emailService = emailService;
    }

    public void generateAndSend(String email) {
        otpRepo.deleteAllByEmail(email);
        String code = String.format("%06d", secureRandom.nextInt(1_000_000));
        otpRepo.save(new OtpToken(email, code, OTP_EXPIRY_MINUTES));
        emailService.sendOtp(email, code, userRepo.existsByEmail(email) ? "login" : "sign up");
    }

    public User verify(String email, String code) {
        OtpToken token = otpRepo.findByEmailAndUsedFalse(email)
            .orElseThrow(() -> new IllegalArgumentException("No active OTP. Please request a new code."));

        if (!token.isValid(code)) {
            throw new IllegalArgumentException(
                token.isExpired() ? "OTP expired. Request a new code." : "Invalid OTP code.");
        }

        token.setUsed(true);
        otpRepo.save(token);

        return userRepo.findByEmail(email).orElseGet(() -> {
            String name = email.split("@")[0];
            User newUser = new User(name, email, (String) null);
            newUser.setEmailVerified(true);
            User saved = userRepo.save(newUser);
            emailService.sendWelcome(email, saved.getName());
            return saved;
        });
    }
}
