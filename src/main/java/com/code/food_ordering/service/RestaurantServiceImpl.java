package com.code.food_ordering.service;


import com.code.food_ordering.dto.RestaurantDto;
import com.code.food_ordering.dto.RestaurantRequestDto;
import com.code.food_ordering.entity.Restaurant;
import com.code.food_ordering.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantServiceImpl implements RestaurantService{
    @Override
    public Restaurant createRestaurant(RestaurantRequestDto req) throws Exception {
        return null;
    }

    @Override
    public Restaurant updateRestaurant(Long restaurantId, RestaurantRequestDto req) throws Exception {
        return null;
    }

    @Override
    public void deleteRestaurant(Long restaurantId) throws Exception {

    }

    @Override
    public List<Restaurant> getAllRestaurants() {
        return List.of();
    }

    @Override
    public Restaurant getARestaurantById(Long restaurantId) throws Exception {
        return null;
    }

    @Override
    public List<Restaurant> searchRestaurant() {
        return List.of();
    }

    @Override
    public Restaurant getRestaurantByUserId(Long userId) throws Exception {
        return null;
    }

    @Override
    public RestaurantDto addToFavourites(Long restaurantId, User user) throws Exception {
        return null;
    }

    @Override
    public Restaurant updateRestaurantStatus(Long restaurantId) throws Exception {
        return null;
    }
}
