package com.example.shop.controller;

import com.example.shop.domain.Product;
import com.example.shop.domain.Views;
import com.example.shop.dto.EventType;
import com.example.shop.dto.ObjectType;
import com.example.shop.dto.ProductPageDto;
import com.example.shop.service.CustomUserService;
import com.example.shop.service.ProductService;
import com.example.shop.util.WsSender;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;
import java.io.IOException;
import java.util.function.BiConsumer;

@RestController
@RequestMapping("products")

public class ProductController {

    public static final int PRODUCTS_PER_PAGE = 3;
    private final ProductService productService;
    private final BiConsumer<EventType, Product> wsSender;

    @Autowired
    public ProductController(ProductService productService, CustomUserService customUserService, WsSender wsSender) {
        this.productService = productService;
        this.wsSender = wsSender.getSender(ObjectType.MESSAGE, Views.IdName.class);
    }

    @PostMapping
    public Product create(@RequestBody Product product,
                          @AuthenticationPrincipal OidcUser oidcUser
                          ) throws IOException {

        wsSender.accept(EventType.CREATE, product);
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
        @PutMapping
    public Product update(@RequestBody Product product) throws IOException {
        Product updateProduct = productService.update(product);
        wsSender.accept(EventType.UPDATE, updateProduct);
        return updateProduct;
    }


    @DeleteMapping("{id}")
    public void delete(@PathVariable ("id") Product product) {

         productService.delete(product);
         wsSender.accept(EventType.REMOVE, product);
    }


}

