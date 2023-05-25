package com.example.shop.controller;

import com.example.shop.domain.Comment;
import com.example.shop.domain.Views;
import com.example.shop.repository.UserRepository;
import com.example.shop.service.CommentService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("comment")
public class CommentController {
    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }
    @PostMapping
    @JsonView(Views.FullProduct.class)
    public Comment create (
            @RequestBody Comment comment,
            @AuthenticationPrincipal OidcUser oidcUser
            ){

        return commentService.create(comment, oidcUser);
    }

}
