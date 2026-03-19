package org.wolf.oauth2;

import org.wolf.model.AuthProvider;
import org.wolf.model.User;
import org.wolf.repository.UserRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    public CustomOAuth2UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oauthUser = super.loadUser(userRequest);
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        OAuth2UserInfo info = new OAuth2UserInfo(oauthUser.getAttributes());

        if (info.getEmail() == null || info.getEmail().isBlank())
            throw new OAuth2AuthenticationException("Email not provided by OAuth2 provider.");

        User user = userRepository.findByEmail(info.getEmail())
            .map(existing -> updateExisting(existing, info, registrationId))
            .orElseGet(() -> createNew(info, registrationId));

        return new OAuth2UserPrincipal(user, oauthUser.getAttributes());
    }

    private User updateExisting(User existing, OAuth2UserInfo info, String registrationId) {
        AuthProvider incoming = AuthProvider.valueOf(registrationId.toUpperCase());
        if (existing.getProvider() != incoming)
            throw new OAuth2AuthenticationException(
                "Account registered with " + existing.getProvider() + ". Please use that login method.");
        existing.setName(info.getName());
        existing.setImageUrl(info.getImageUrl());
        return userRepository.save(existing);
    }

    private User createNew(OAuth2UserInfo info, String registrationId) {
        AuthProvider provider = AuthProvider.valueOf(registrationId.toUpperCase());
        return userRepository.save(new User(info.getName(), info.getEmail(),
            provider, info.getId(), info.getImageUrl()));
    }
}
