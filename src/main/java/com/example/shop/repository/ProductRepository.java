package com.example.shop.repository;

import com.example.shop.domain.Product;

import com.example.shop.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ProductRepository extends JpaRepository <Product, Long> {

    @EntityGraph (attributePaths = {"comments"})
    Page<Product> findByAuthorIn(List<User> users, Pageable pageable);

}
