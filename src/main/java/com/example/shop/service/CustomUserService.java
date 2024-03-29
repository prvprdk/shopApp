package com.example.shop.service;


import com.example.shop.config.GoogleUserInfo;
import com.example.shop.domain.User;
import com.example.shop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service

public class CustomUserService extends OidcUserService {

    @Autowired
private UserRepository userRepository;

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        OidcUser oidcUser = super.loadUser(userRequest);
        try {
            return processOidcUser(userRequest, oidcUser);
        } catch (Exception ex) {
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    private OidcUser processOidcUser(OidcUserRequest userRequest, OidcUser oidcUser) {

        GoogleUserInfo googleUserInfo = new GoogleUserInfo(oidcUser.getAttributes());
        Optional<User> userOptional = userRepository.findById(googleUserInfo.getId());
        if (userOptional.isEmpty()){
            userRepository.save(convertedUser(oidcUser));
        }
        return oidcUser;
    }

    public User convertedUser (OidcUser oidcUser){
        GoogleUserInfo googleUserInfo = new GoogleUserInfo(oidcUser.getAttributes());
        User user = new User();
        user.setId(googleUserInfo.getId());
        user.setEmail(googleUserInfo.getEmail());
        user.setName(googleUserInfo.getName());
        user.setPicture(googleUserInfo.getPicture());
        user.setLocale(googleUserInfo.getLocale());
        return    user;

    }

}
