package com.example.shop.domain;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table (name = "usr")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @JsonView(Views.IdName.class)
    private String id;
    private String email;
    private String name;
    @JsonView(Views.IdName.class)
    private String picture;
    private String locale;

}
