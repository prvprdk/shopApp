package com.example.shop.service;


import com.example.shop.domain.User;
import com.example.shop.domain.UserSubscription;
import com.example.shop.repository.UserRepository;
import com.example.shop.repository.UserSubscriptionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfileService {
    private final UserRepository userRepository;
    private final UserSubscriptionRepo userSubscriptionRepo;

    @Autowired
    public ProfileService(UserRepository userRepository, UserSubscriptionRepo userSubscriptionRepo) {
        this.userRepository = userRepository;
        this.userSubscriptionRepo = userSubscriptionRepo;
    }


    public User changeSubscription(User channel, String idSubscriber) {

        List<UserSubscription> subscriptions = channel.getSubscribers()
                .stream()
                .filter(subscription ->
                        subscription.getSubscriber().getId().equals(idSubscriber))
                .toList();

        if (subscriptions.isEmpty()) {
            UserSubscription subscription = new UserSubscription(channel, userRepository.findById(idSubscriber).get());
            channel.getSubscribers().add(subscription);
        } else {
            subscriptions.forEach(channel.getSubscribers()::remove);
        }
        userRepository.save(channel);
        return channel;

    }

    public List<UserSubscription> getSubscribers(User channel) {
        return userSubscriptionRepo.findByChannel(channel);
    }

    public UserSubscription changeSubscriptionStatus(User channel, User subscriber) {
        UserSubscription subscription = userSubscriptionRepo.findByChannelAndSubscriber(channel, subscriber);
        subscription.setActive(!subscription.isActive());
        return userSubscriptionRepo.save(subscription);
    }


}
