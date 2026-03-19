package org.wolf.dto;
import jakarta.validation.constraints.*;

public class OtpVerifyRequest {
    @NotBlank @Email            private String email;
    @NotBlank @Size(min=6,max=6) private String code;

    public OtpVerifyRequest() {}
    public String getEmail()       { return email; }
    public void setEmail(String e) { this.email = e; }
    public String getCode()        { return code; }
    public void setCode(String c)  { this.code = c; }
}
