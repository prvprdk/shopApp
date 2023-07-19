package com.example.shop.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table (name = "usr")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = {"id", "name"} )
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



    @JsonView(Views.FullProfile.class)
    @OneToMany (mappedBy = "subscriber",
    orphanRemoval = true )
    private Set<UserSubscription> subscriptions = new HashSet<>();


    @JsonView(Views.FullProfile.class)
    @OneToMany (mappedBy = "channel",
            orphanRemoval = true,
            cascade = CascadeType.ALL
    )
    private Set<UserSubscription> subscribers = new HashSet<>();

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
