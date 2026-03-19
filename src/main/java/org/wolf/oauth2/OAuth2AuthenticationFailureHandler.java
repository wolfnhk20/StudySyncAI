package org.wolf.oauth2;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
public class OAuth2AuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Value("${app.oauth2.redirect-uri:http://localhost:3000/oauth-callback}")
    private String redirectUri;

    @Override
    public void onAuthenticationFailure(HttpServletRequest req, HttpServletResponse res,
                                        AuthenticationException ex) throws IOException {
        String error = URLEncoder.encode(
            ex.getMessage() != null ? ex.getMessage() : "OAuth2 login failed",
            StandardCharsets.UTF_8);
        String url = UriComponentsBuilder.fromUriString(redirectUri)
            .queryParam("error", error).build().toUriString();
        getRedirectStrategy().sendRedirect(req, res, url);
    }
}
