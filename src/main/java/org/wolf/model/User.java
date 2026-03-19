package org.wolf.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Email
    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @Column
    private String password;  // null for OAuth users

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private AuthProvider provider = AuthProvider.LOCAL;

    @Column(name = "provider_id")
    private String providerId;

    @Column(name = "email_verified", nullable = false)
    private boolean emailVerified = false;

    @Column(name = "image_url")
    private String imageUrl;

    protected User() {}

    /** For LOCAL (email/password) registration */
    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.provider = AuthProvider.LOCAL;
    }

    /** For OAuth2 registration — no password */
    public User(String name, String email, AuthProvider provider,
                String providerId, String imageUrl) {
        this.name = name;
        this.email = email;
        this.provider = provider;
        this.providerId = providerId;
        this.imageUrl = imageUrl;
        this.emailVerified = true;
    }

    public Long getId()           { return id; }
    public String getName()       { return name; }
    public void setName(String n) { this.name = n; }
    public String getEmail()      { return email; }
    public void setEmail(String e){ this.email = e; }
    public String getPassword()            { return password; }
    public void setPassword(String p)      { this.password = p; }
    public AuthProvider getProvider()              { return provider; }
    public void setProvider(AuthProvider p)        { this.provider = p; }
    public String getProviderId()                  { return providerId; }
    public void setProviderId(String id)           { this.providerId = id; }
    public boolean isEmailVerified()               { return emailVerified; }
    public void setEmailVerified(boolean v)        { this.emailVerified = v; }
    public String getImageUrl()                    { return imageUrl; }
    public void setImageUrl(String url)            { this.imageUrl = url; }
}
