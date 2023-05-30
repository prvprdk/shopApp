package com.example.shop.domain;


import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@Table
@EqualsAndHashCode(of = {"id"})
public class Comment {

    @Id
    @GeneratedValue
    @JsonView(Views.IdName.class)
    private Long id;
    @JsonView (Views.IdName.class)
    private String text;
    @ManyToOne
    @JoinColumn (name="product_id")
    @JsonView (Views.IdName.class)

    private Product product;
    @ManyToOne
    @JoinColumn (name = "user_id", nullable = false, updatable = false)
    @JsonView (Views.IdName.class)
    private User author;
}
