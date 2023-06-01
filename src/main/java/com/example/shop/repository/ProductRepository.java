package com.example.shop.repository;

import com.example.shop.domain.Product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;



public interface ProductRepository extends JpaRepository <Product, Long> {

    @EntityGraph (attributePaths = {"comments"})
    Page<Product> findAll( Pageable pageable);

}
