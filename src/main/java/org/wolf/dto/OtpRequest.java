package org.wolf.dto;
import jakarta.validation.constraints.*;

public class OtpRequest {
    @NotBlank @Email private String email;
    public OtpRequest() {}
    public String getEmail()       { return email; }
    public void setEmail(String e) { this.email = e; }
}
