package com.example.shop.controller;

import com.example.shop.domain.Views;
import com.example.shop.dto.ProductPageDto;
import com.example.shop.service.CustomUserService;
import com.example.shop.service.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;

import static com.example.shop.controller.ProductController.PRODUCTS_PER_PAGE;

@Controller
@RequestMapping("/")
public class MainController {

    private final ProductService productService;
    private final CustomUserService customUserService;
    private final ObjectWriter writer;

    @Value("${spring.profiles.active}")
    private String profile;


    @Autowired
    public MainController (ProductService productService, CustomUserService customUserService, ObjectMapper mapper){
        this.productService=productService;
        this.customUserService = customUserService;
        this.writer = mapper.setConfig(mapper.getSerializationConfig())
                .writerWithView((Views.FullProduct.class));
    }

    @GetMapping
    public String mainController (Model model, @AuthenticationPrincipal OidcUser user) throws JsonProcessingException {
        HashMap<Object, Object> data = new HashMap<Object, Object>();
        if (user != null) {
            data.put("profile", customUserService.convertedUser(user));

            Sort sort = Sort.by(Sort.Direction.DESC, "id");
            PageRequest pageRequest = PageRequest.of(0, PRODUCTS_PER_PAGE, sort);
            ProductPageDto all = productService.readAll( pageRequest );

            String products = writer.writeValueAsString(all.getProducts());

            data.put("currentPage", all.getCurrentPage());
            data.put("totalPages", all.getTotalPage());

            model.addAttribute("products", products);
        }else {
            model.addAttribute("products", "[]");

        }
            model.addAttribute("frontendData", data);
            model.addAttribute("isDevMode", "dev".equals(profile));
            return "index";

    }

}
