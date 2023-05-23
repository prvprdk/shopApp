package com.example.shop.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @JsonView (Views.IdName.class)
    private Long id;
    @JsonView (Views.IdName.class)
    private String name;
    @Column(updatable = false)
    @JsonView(Views.FullProduct.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime localDateTime;
    @JsonView(Views.FullProduct.class)
    private String link;
    @JsonView(Views.FullProduct.class)
    private String linkTitle;
    @JsonView(Views.FullProduct.class)
    private String linkDescription;
    @JsonView(Views.FullProduct.class)
    private String linkCover;


}
