package com.example.shop.controller;

import com.example.shop.domain.Product;
import com.example.shop.domain.Views;
import com.example.shop.dto.ProductPageDto;
import com.example.shop.service.CustomUserService;
import com.example.shop.service.ProductService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;
import java.io.IOException;

@RestController
@RequestMapping("products")

public class ProductController {

    public static final int PRODUCTS_PER_PAGE = 3;
    private final ProductService productService;
    private final CustomUserService customUserService;


    @Autowired
    public ProductController(ProductService productService, CustomUserService customUserService) {
        this.productService = productService;
        this.customUserService = customUserService;
    }

    @PostMapping
    @JsonView(Views.FullProduct.class)
    public Product create(@RequestBody Product product,
                          @AuthenticationPrincipal OidcUser oidcUser
                          ) throws IOException {

        return productService.create(product, oidcUser);
    }

    @GetMapping
    @JsonView(Views.FullProduct.class)
    public ProductPageDto list(
            @PageableDefault (size = PRODUCTS_PER_PAGE, sort =  {"id"}, direction = Sort.Direction.DESC) Pageable pageable
    ){
        return productService.readAll(pageable);
    }

    @GetMapping("{id}")
    @JsonView(Views.FullProduct.class)
    public Product getOne(@PathVariable("id") Product product) {
        return product;
    }

        @PutMapping ("{id}")
        @JsonView(Views.FullProduct.class)
    public Product update(@PathVariable("id") Product productFromDb,@RequestBody Product product) throws IOException {

        return productService.update(product, productFromDb);
    }


    @DeleteMapping("{id}")
    public void delete(@PathVariable ("id") Product product) {
         productService.delete(product);

    }


}

