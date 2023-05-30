package com.example.shop.domain;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)

public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @JsonView (Views.Id.class)
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
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonView (Views.IdName.class)
    private User author;
    @OneToMany (mappedBy = "product", orphanRemoval = true)
    @JsonView(Views.IdName.class)

    private List<Comment> comments;


}
