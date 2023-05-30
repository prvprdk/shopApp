package com.example.shop.service;

import com.example.shop.domain.Comment;
import com.example.shop.domain.Views;
import com.example.shop.dto.EventType;
import com.example.shop.dto.ObjectType;
import com.example.shop.repository.CommentRepo;
import com.example.shop.util.WsSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import java.util.function.BiConsumer;

@Service
public class CommentService {
    private final CommentRepo commentRepo;
    private final CustomUserService customUserService;
    private final BiConsumer<EventType, Comment> wsSender;

    @Autowired
    public CommentService(CommentRepo commentRepo, CustomUserService customUserService, WsSender wsSender) {
        this.commentRepo = commentRepo;
        this.customUserService = customUserService;
        this.wsSender = wsSender.getSender(ObjectType.COMMENT, Views.FullComment.class);
    }


    public Comment create (Comment comment, OidcUser oidcUser){
        comment.setAuthor(customUserService.convertedUser(oidcUser));
        Comment commentFromDB = commentRepo.save(comment);

        wsSender.accept(EventType.CREATE, commentFromDB);
        return commentFromDB;
    }
}
