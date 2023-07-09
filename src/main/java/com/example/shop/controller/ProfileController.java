package com.example.shop.controller;


import com.example.shop.domain.User;
import com.example.shop.domain.Views;
import com.example.shop.repository.UserRepository;
import com.example.shop.service.CustomUserService;
import com.example.shop.service.ProfileService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("profile")
public class ProfileController {
    private final ProfileService profileService;
    private final CustomUserService customUserService;
    private UserRepository userRepository;

    @Autowired
    public ProfileController(ProfileService profileService, CustomUserService customUserService, UserRepository userRepository) {
        this.profileService = profileService;
        this.customUserService = customUserService;
        this.userRepository = userRepository;
    }

    @GetMapping ("{id}")
    @JsonView(Views.FullProfile.class)
    public User get (@PathVariable ("id") User user){
        return user;
    }

    @PostMapping ("change-subscription/{channelId}")
    @JsonView (Views.FullProfile.class)
    public User changeSubscriptions (
            @AuthenticationPrincipal OidcUser oidcUser,
            @PathVariable ("channelId") User channel
            ) {


        User subscriber = userRepository.findById(Objects.requireNonNull(oidcUser.getAttribute("sub"))).get();

        if (subscriber.equals(channel)) {
            return channel;
        } else {
            return profileService.changeSubscription(channel, subscriber);
        }
    }
}
