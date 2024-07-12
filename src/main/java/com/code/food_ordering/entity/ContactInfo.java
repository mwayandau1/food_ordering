package com.code.food_ordering.entity;


import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class ContactInfo {
    private String mobile;

    private String email;

    private String x;

    private String instagram;
}
