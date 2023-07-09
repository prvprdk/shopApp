package com.example.shop.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table (name = "usr")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    @Id
    @JsonView(Views.IdName.class)
    private String id;
    private String email;
    @JsonView(Views.IdName.class)
    private String name;
    @JsonView(Views.IdName.class)
    private String picture;
    @JsonView(Views.FullProfile.class)
    private String locale;


    @ManyToMany
    @JsonView(Views.FullProfile.class)
    @JoinTable (
            name = "user_subscriptions",
            joinColumns = @JoinColumn(name="subscriber_id"),
            inverseJoinColumns = @JoinColumn(name ="channel_id")
    )
    @JsonIdentityInfo(
            property = "id",
            generator = ObjectIdGenerators.PropertyGenerator.class
    )
    @JsonIdentityReference
    private Set<User> subscriptions = new HashSet<>();
    @ManyToMany
    @JsonView(Views.FullProfile.class)
    @JsonIdentityInfo(
            property = "id",
            generator = ObjectIdGenerators.PropertyGenerator.class
    )
    @JsonIdentityReference
    @JoinTable (
            name = "user_subscriptions",
            joinColumns = @JoinColumn(name ="channel_id"),
            inverseJoinColumns = @JoinColumn(name="subscriber_id")
    )
    private Set<User> subscribers = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
