package com.code.food_ordering.dto;

import com.code.food_ordering.entity.Address;
import com.code.food_ordering.entity.ContactInfo;
import lombok.Data;

import java.util.List;

@Data
public class RestaurantRequestDto {

    private Long id;

    private String name;

    private String description;

    private String cuisineType;

    private Address address;

    private ContactInfo contactInfo;

    private String openingHours;

    private List<String> images;
}
