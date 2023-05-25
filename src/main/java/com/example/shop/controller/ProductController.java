package com.example.shop.controller;

import com.example.shop.domain.Product;
import com.example.shop.domain.Views;
import com.example.shop.dto.EventType;
import com.example.shop.dto.ObjectType;
import com.example.shop.dto.ProductDTO;
import com.example.shop.service.CustomUserService;
import com.example.shop.service.ProductService;
import com.example.shop.util.WsSender;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.function.BiConsumer;

@RestController
@RequestMapping("products")

public class ProductController {


    private final ProductService productService;
    private final BiConsumer<EventType, Product> wsSender;

    @Autowired
    public ProductController(ProductService productService, CustomUserService customUserService, WsSender wsSender) {
        this.productService = productService;
        this.wsSender = wsSender.getSender(ObjectType.MESSAGE, Views.IdName.class);
    }

    @PostMapping
    public Product create(@RequestBody ProductDTO dto,
                          @AuthenticationPrincipal OidcUser oidcUser
                          ) throws IOException {
        Product product = productService.create(dto, oidcUser);
        wsSender.accept(EventType.CREATE, product);

        return product;
    }

    @GetMapping
    @JsonView(Views.IdName.class)
    public ResponseEntity<List<Product>> readAll() {
        return new ResponseEntity<>(productService.readAll(), HttpStatus.OK);
    }

        @PutMapping("{id}")
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

