package com.example.herehere.auth;

import com.example.herehere.user.entity.User;
import com.example.herehere.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
    private final OAuth2AuthorizedClientService authorizedClientService;

    public CustomOAuth2UserService(UserRepository userRepository, OAuth2AuthorizedClientService oAuth2AuthorizedClientService) {
        this.userRepository = userRepository;
        this.authorizedClientService = oAuth2AuthorizedClientService;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);
        log.info("getAttributes : {}", oAuth2User.getAttributes());
        OAuth2AccessToken accessToken = userRequest.getAccessToken();

        log.info("accessToken : {}", accessToken.getTokenValue());

        ClientRegistration clientRegistration = userRequest.getClientRegistration();
        log.info("Client Registration : {}", clientRegistration);

        String provider = userRequest.getClientRegistration().getRegistrationId();

        OAuth2UserInfo oAuth2UserInfo = null;

        if(provider.equals("kakao")) oAuth2UserInfo = new KakaoUserInfo((Map) oAuth2User.getAttributes());

        String providerId = oAuth2UserInfo.getProviderId();
        String email = oAuth2UserInfo.getEmail();
        String nickname = oAuth2UserInfo.getName();

        log.info(email + nickname);

        Optional<User> optionalUser = userRepository.findByEmail(email);
        User user = null;

        if(optionalUser.isEmpty()){
            user = User.builder()
                    .email(email)
                    .nickName(nickname)
                    .build();
            userRepository.save(user);
        }

        else user = optionalUser.get();

        return oAuth2User;

    }

    public void testRefresh(Principal principal){
        String clientRegistrationId = "kakao";

        OAuth2AuthorizedClient authorizedClient = this.authorizedClientService.loadAuthorizedClient(clientRegistrationId, principal.getName());

        OAuth2RefreshToken refreshToken = authorizedClient.getRefreshToken();
        if (refreshToken != null) {
            log.info("refreshToken : {}", refreshToken.getTokenValue());
        }

    }




}
