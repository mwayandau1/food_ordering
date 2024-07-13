package com.code.food_ordering.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "_users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String fullName;

    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private ROLE role = ROLE.CUSTOMER;

    @JsonIgnore
    @OneToMany(mappedBy = "customer", cascade =CascadeType.ALL,  orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();

    @ElementCollection
    private List<Restaurant> favorites = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL,  orphanRemoval = true)
    private List<Address> addresses = new ArrayList<>();


}
