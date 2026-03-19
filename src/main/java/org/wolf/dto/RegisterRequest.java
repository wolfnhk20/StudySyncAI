package org.wolf.dto;
import jakarta.validation.constraints.*;

public class RegisterRequest {
    @NotBlank @Size(min=2, max=100) private String name;
    @NotBlank @Email                private String email;
    @NotBlank @Size(min=6)          private String password;

    public RegisterRequest() {}
    public String getName()            { return name; }
    public void setName(String n)      { this.name = n; }
    public String getEmail()           { return email; }
    public void setEmail(String e)     { this.email = e; }
    public String getPassword()        { return password; }
    public void setPassword(String p)  { this.password = p; }
}
