package org.wolf.dto;
import org.wolf.model.User;

public class UserResponse {
    private final Long id;
    private final String name;
    private final String email;
    private final String provider;

    public UserResponse(Long id, String name, String email, String provider) {
        this.id = id; this.name = name; this.email = email; this.provider = provider;
    }

    public static UserResponse fromEntity(User u) {
        return new UserResponse(u.getId(), u.getName(), u.getEmail(), u.getProvider().name());
    }

    public Long getId()        { return id; }
    public String getName()    { return name; }
    public String getEmail()   { return email; }
    public String getProvider(){ return provider; }
}
