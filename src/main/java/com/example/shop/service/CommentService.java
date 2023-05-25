package com.example.shop.service;

import com.example.shop.domain.Comment;
import com.example.shop.repository.CommentRepo;
import com.example.shop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    private final CommentRepo commentRepo;
    private final CustomUserService customUserService;

    @Autowired
    public CommentService(CommentRepo commentRepo, UserRepository userRepository, CustomUserService customUserService) {
        this.commentRepo = commentRepo;
        this.customUserService = customUserService;
    }

    public Comment create (Comment comment, OidcUser oidcUser){
        comment.setAuthor(customUserService.convertedUser(oidcUser));
        commentRepo.save(comment);
        return comment;
    }
}
