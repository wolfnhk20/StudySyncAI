package org.wolf.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "otp_tokens")
public class OtpToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String email;

    @Column(nullable = false, length = 10)
    private String code;

    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;

    @Column(nullable = false)
    private boolean used = false;

    protected OtpToken() {}

    public OtpToken(String email, String code, int expiryMinutes) {
        this.email = email;
        this.code = code;
        this.expiresAt = LocalDateTime.now().plusMinutes(expiryMinutes);
    }

    public boolean isExpired() { return LocalDateTime.now().isAfter(expiresAt); }
    public boolean isValid(String input) { return !used && !isExpired() && code.equals(input); }

    public Long getId()           { return id; }
    public String getEmail()      { return email; }
    public String getCode()       { return code; }
    public LocalDateTime getExpiresAt() { return expiresAt; }
    public boolean isUsed()       { return used; }
    public void setUsed(boolean u){ this.used = u; }
}
