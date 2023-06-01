package com.example.shop.dto;


import com.example.shop.domain.Product;
import com.example.shop.domain.Views;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@JsonView (Views.FullProduct.class)
public class ProductPageDto {

    private List<Product> products;
    private int currentPage;
    private int totalPage;

}
