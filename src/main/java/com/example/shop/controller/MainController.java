package com.example.shop.controller;

import com.example.shop.domain.User;
import com.example.shop.repository.ProductRepository;
import com.example.shop.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.aspectj.bridge.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;

@Controller
@RequestMapping("/")
public class MainController {

    private final ProductRepository productRepository;

   @Value("${spring.profiles.active}")
    private String profile;

    @Autowired
    public MainController (ProductRepository productRepository){
        this.productRepository=productRepository;
    }

    @GetMapping
    public String mainController (Model model, @AuthenticationPrincipal OidcUser user){
        HashMap<Object, Object> data = new HashMap<Object, Object>();
        if (user != null) {
            data.put("profile", user);
            data.put("products", productRepository.findAll());
        }
            model.addAttribute("frontendData", data);
            model.addAttribute("isDevMode", "dev".equals(profile));
            return "index";

    }


}
