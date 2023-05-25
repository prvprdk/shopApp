package com.example.shop.controller;

import com.example.shop.domain.Views;
import com.example.shop.repository.ProductRepository;
import com.example.shop.service.CustomUserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.util.HashMap;

@Controller
@RequestMapping("/")
public class MainController {

    private final ProductRepository productRepository;
    private final CustomUserService customUserService;
    private final ObjectWriter writer;

    @Value("${spring.profiles.active}")
    private String profile;


    @Autowired
    public MainController (ProductRepository productRepository, CustomUserService customUserService, ObjectMapper mapper){
        this.productRepository=productRepository;
        this.customUserService = customUserService;
        this.writer = mapper.setConfig(mapper.getSerializationConfig())
                .writerWithView((Views.FullProduct.class));
    }

    @GetMapping
    public String mainController (Model model, @AuthenticationPrincipal OidcUser user) throws JsonProcessingException {
        HashMap<Object, Object> data = new HashMap<Object, Object>();
        if (user != null) {
            data.put("profile", customUserService.convertedUser(user));
            String products = writer.writeValueAsString(productRepository.findAll());
            model.addAttribute("products", products);
        }
            model.addAttribute("frontendData", data);
            model.addAttribute("isDevMode", "dev".equals(profile));
            return "index";

    }

}
