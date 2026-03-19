package org.wolf.oauth2;

import org.wolf.model.User;
import org.wolf.security.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;
import java.io.IOException;

@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtUtils jwtUtils;

    @Value("${app.oauth2.redirect-uri:http://localhost:3000/oauth-callback}")
    private String redirectUri;

    public OAuth2AuthenticationSuccessHandler(JwtUtils jwtUtils) { this.jwtUtils = jwtUtils; }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse res,
                                        Authentication auth) throws IOException {
        OAuth2UserPrincipal principal = (OAuth2UserPrincipal) auth.getPrincipal();
        User user = principal.getUser();
        String token = jwtUtils.generateToken(user.getEmail());
        String url = UriComponentsBuilder.fromUriString(redirectUri)
            .queryParam("token", token)
            .queryParam("userId", user.getId())
            .queryParam("name", user.getName())
            .build().toUriString();
        getRedirectStrategy().sendRedirect(req, res, url);
    }
}
