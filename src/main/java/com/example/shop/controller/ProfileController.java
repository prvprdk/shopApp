package com.example.shop.controller;


import com.example.shop.domain.User;
import com.example.shop.domain.UserSubscription;
import com.example.shop.domain.Views;
import com.example.shop.repository.UserRepository;
import com.example.shop.service.ProfileService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("profile")
public class ProfileController {
    private final ProfileService profileService;
    private final UserRepository userRepository;

    @Autowired
    public ProfileController(ProfileService profileService, UserRepository userRepository) {
        this.profileService = profileService;
        this.userRepository = userRepository;
    }

    @GetMapping("{id}")
    @JsonView(Views.FullProfile.class)
    public User get(@PathVariable("id") User user) {
        return user;
    }

    @PostMapping("change-subscription/{channelId}")
    @JsonView(Views.FullProfile.class)
    public User changeSubscriptions(
            @AuthenticationPrincipal OidcUser oidcUser,
            @PathVariable("channelId") User channel
    ) {
        String idSubscriber = oidcUser.getAttribute("sub");
        if (Objects.equals(idSubscriber, channel.getId())) {
            return channel;
        } else {
            return profileService.changeSubscription(channel, idSubscriber);
        }
    }

    @GetMapping("get-subscribers/{channelId}")
    @JsonView(Views.IdName.class)
    public List<UserSubscription> subscribers(
            @PathVariable("channelId") User channel) {
        return profileService.getSubscribers(channel);
    }

    @PostMapping("change-status/{subscriberId}")
    @JsonView(Views.IdName.class)
    public UserSubscription changeSubscriptionStatus(
            @AuthenticationPrincipal OidcUser channel,
            @PathVariable("subscriberId") User subscriber) {
        return profileService.changeSubscriptionStatus(userRepository.findById(Objects.requireNonNull(channel.getAttribute("sub"))).get(), subscriber);
    }


}
