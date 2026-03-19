package org.wolf.email;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private static final Logger log = LoggerFactory.getLogger(EmailService.class);

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromAddress;

    @Value("${app.name:StudySync AI}")
    private String appName;

    public EmailService(JavaMailSender mailSender) { this.mailSender = mailSender; }

    public void sendOtp(String toEmail, String otp, String purpose) {
        send(toEmail,
            appName + " — Your " + purpose + " code: " + otp,
            "Your " + purpose + " code is: " + otp + "\n\nExpires in 10 minutes. Do not share it.");
    }

    public void sendWelcome(String toEmail, String name) {
        send(toEmail,
            "Welcome to " + appName + ", " + name + "!",
            "Hi " + name + ",\n\nWelcome to " + appName + "! You're all set.\n\nHappy studying!");
    }

    private void send(String to, String subject, String body) {
        try {
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setFrom(fromAddress);
            msg.setTo(to);
            msg.setSubject(subject);
            msg.setText(body);
            mailSender.send(msg);
            log.info("Email sent → {}: {}", to, subject);
        } catch (Exception e) {
            log.error("Email failed → {}: {}", to, e.getMessage());
        }
    }
}
