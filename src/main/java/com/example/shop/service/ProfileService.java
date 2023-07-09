package com.example.shop.service;


import com.example.shop.domain.User;
import com.example.shop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ProfileService {
    private final UserRepository userRepository;

    @Autowired
    public ProfileService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public User changeSubscription(User channel, User subscriber) {

        Set<User> subscribers = channel.getSubscribers();
        if (subscribers.contains(subscriber)){
            subscribers.remove(subscriber);
        }else {
            subscribers.add(subscriber);
        }
        userRepository.save(channel);
        return channel;

    }
}
